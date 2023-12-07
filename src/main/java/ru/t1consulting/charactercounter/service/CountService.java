package ru.t1consulting.charactercounter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.t1consulting.charactercounter.GeneralProperties;
import ru.t1consulting.charactercounter.dto.CommonRs;
import ru.t1consulting.charactercounter.exception.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountService {

    private final GeneralProperties generalProperties;

    public CommonRs countPlainTextCharacters(String stringInput) {
        validateInputString(stringInput);

        Map<Character, Long> characterCountMap = stringInput.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return new CommonRs(characterCountMap, "plainText");
    }

    private void validateInputString(String stringInput) {
        if (stringInput == null || stringInput.isEmpty()) {
            throw new EmptyInputStringException("Input String is empty!");
        } else if (stringInput.length() > generalProperties.getMaxPlainStringLength()) {
            throw new InputStringIsTooLongException("Input String should not be longer then " +
                    generalProperties.getMaxPlainStringLength() + " characters");
        }
    }


    public CommonRs countFileCharacters(MultipartFile file) {

        validateInputFile(file);

        Map<Character, Long> charactersCountMap;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            charactersCountMap = reader.lines()
                    .parallel()
                    .flatMapToInt(String::chars)
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.groupingByConcurrent(Function.identity(), Collectors.counting()));
        } catch (IOException e) {
            throw new BadRequestException("Something wrong with file :((");
        }

        String originalFilename = file.getOriginalFilename();
        int lastIndexOfDot = Objects.requireNonNull(originalFilename).lastIndexOf('.');
        String fileExtension = originalFilename.substring(lastIndexOfDot);

        return new CommonRs(charactersCountMap, fileExtension);
    }

    private void validateInputFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("The file is empty");
        }

        int fileLength;
        try {
            fileLength = file.getBytes().length;
        } catch (IOException e) {
            throw new BadRequestException("Error occurred during file parsing " +
                    "check file and try again");
        }

        if (fileLength > generalProperties.getMaxFileSizeBytes()) {
            throw new InputFileIsTooBigException("File size should not be larger then " +
                    generalProperties.getMaxFileSizeBytes() + " bytes");
        } else {
            String originalFilename = file.getOriginalFilename();
            int lastIndexOfDot = Objects.requireNonNull(originalFilename).lastIndexOf(".");
            String fileExtension = originalFilename.substring(lastIndexOfDot);

            if (!generalProperties.getTypesAllowed().contains(fileExtension)) {
                throw new FileTypeNotSupportedException("File type is not supported. " +
                        "Supported file types: " + generalProperties.getTypesAllowed());
            }
        }
    }
}
