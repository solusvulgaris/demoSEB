package com.ak.springbootdemo.seb.service;

import com.ak.springbootdemo.seb.data.Subsidiary;
import com.ak.springbootdemo.seb.data.SubsidiaryRepository;
import com.ak.springbootdemo.seb.exceptions.SubsidiaryServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("SubsidiaryService tests")
class SubsidiaryServiceTest {
    final static SubsidiaryRepository subsidiaryRepository = Mockito.mock(SubsidiaryRepository.class);
    final SubsidiaryService subsidiaryService = new SubsidiaryService(subsidiaryRepository);
    static final Subsidiary validSubsidiaryExample = new Subsidiary(
            "VS","Test", "Test Valid Subsidiary", "111-111-111");
    final Subsidiary updatedSubsidiaryExample = new Subsidiary(
            validSubsidiaryExample.getInnerCode(),
            validSubsidiaryExample.getAddress(),
            "Updated Test Valid Subsidiary",
            validSubsidiaryExample.getPhoneNumber());

    @BeforeAll
    static void setUpAll() {
        when(subsidiaryRepository.save(any(Subsidiary.class))).then(returnsFirstArg());
    }

    public static Stream<Arguments> createSubsidiaryInputParams() {
        return Stream.of(
                Arguments.of(Optional.of(validSubsidiaryExample)),
                Arguments.of(Optional.empty())
        );
    }

    @ParameterizedTest
    @MethodSource("createSubsidiaryInputParams")
    @DisplayName("createSubsidiary() test cases")
    void createSubsidiaryTest(Optional<Subsidiary> subsidiary) {
        when(subsidiaryRepository.findByInnerCode(anyString())).thenReturn(subsidiary);
        Assertions.assertEquals(updatedSubsidiaryExample, subsidiaryService.createSubsidiary(
                updatedSubsidiaryExample.getInnerCode(),
                updatedSubsidiaryExample.getAddress(),
                updatedSubsidiaryExample.getName(),
                updatedSubsidiaryExample.getPhoneNumber()));
    }

    @Test
    @DisplayName("Invalid input to the addSubsidiary()")
    void invalidInputAddSubsidiaryTest() {
        final Class<SubsidiaryServiceException> expectedExceptionClass = SubsidiaryServiceException.class;
        final String expectedExceptionMessage = "Subsidiary cannot be null.";

        final Exception actualException = Assertions.assertThrows(
                expectedExceptionClass,
                () -> subsidiaryService.addSubsidiary(null)
        );
        Assertions.assertEquals(expectedExceptionClass, actualException.getClass(), "Exception class");
        Assertions.assertEquals(expectedExceptionMessage, actualException.getMessage(), "Exception message");
    }

    @Test
    @DisplayName("Valid input to the addSubsidiary()")
    void validInputAddSubsidiaryTest() {
        Assertions.assertEquals(validSubsidiaryExample, subsidiaryService.addSubsidiary(validSubsidiaryExample));
    }

}
