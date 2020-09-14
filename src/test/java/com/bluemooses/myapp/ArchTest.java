package com.bluemooses.myapp;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.bluemooses.myapp");

        noClasses()
            .that()
            .resideInAnyPackage("com.bluemooses.myapp.service..")
            .or()
            .resideInAnyPackage("com.bluemooses.myapp.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.bluemooses.myapp.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
