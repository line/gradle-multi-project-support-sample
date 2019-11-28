import com.linecorp.support.project.multi.recipe.configure
import com.linecorp.support.project.multi.recipe.configureByTypeHaving
import com.linecorp.support.project.multi.recipe.configureByTypePrefix
import com.linecorp.support.project.multi.recipe.configureByTypeSuffix
import com.linecorp.support.project.multi.recipe.matcher.ProjectMatchers.Companion.byLabel
import com.linecorp.support.project.multi.recipe.matcher.ProjectMatchers.Companion.byTypeHaving
import com.linecorp.support.project.multi.recipe.matcher.ProjectMatchers.Companion.byTypePrefix
import com.linecorp.support.project.multi.recipe.matcher.ProjectMatchers.Companion.byTypeSuffix
import com.linecorp.support.project.multi.recipe.matcher.and
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot").version("2.2.1.RELEASE").apply(false)
    id("io.spring.dependency-management").version("1.0.8.RELEASE").apply(false)
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
    kotlin("kapt") version "1.3.50"
    id("com.linecorp.build-recipe-plugin") version "1.0.1"
    id("com.linecorp.recursive-git-log-plugin") version "1.0.1"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

configureByTypePrefix("kotlin") {
    apply(plugin = "kotlin")

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    }
}

configureByTypeSuffix("lib") {
    apply(plugin = "java-library")
}

configureByTypeHaving("boot") {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-spring")

    configure<DependencyManagementExtension> {
        dependencies {
            dependencySet("com.squareup.retrofit2:2.6.2") {
                entry("retrofit")
                entry("converter-jackson")
            }
            dependencySet("org.jetbrains.kotlinx:1.3.2") {
                entry("kotlinx-coroutines-core")
                entry("kotlinx-coroutines-reactor")
            }
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

configure(byTypePrefix("kotlin") and byTypeHaving("boot")) {
    apply(plugin = "kotlin-kapt")

    dependencies {
        "kapt"("org.springframework.boot:spring-boot-configuration-processor:2.2.1.RELEASE")
    }
}

configure(byTypeHaving("boot") and byTypeSuffix("lib")) {
    tasks {
        withType<BootJar> {
            enabled = false
        }

        withType<Jar> {
            enabled = true
        }

        withType<BootRun> {
            enabled = false
        }
    }
}

configure(byTypeSuffix("boot-application") and byLabel("spring-boot-webflux")) {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    }
}

configure(byTypeSuffix("boot-application") and byLabel("spring-boot-webmvc")) {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
    }
}

configure(byTypeSuffix("boot-lib") and byLabel("retrofit2-client")) {
    dependencies {
        implementation("org.springframework.boot:spring-boot-autoconfigure")
        implementation("org.springframework.boot:spring-boot-starter-validation")

        api("com.squareup.retrofit2:retrofit")
        implementation("com.squareup.retrofit2:converter-jackson")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    }
}
