# Language Detector

An application that detects the language of the input text based on Ngrams computed using human language files.
 
## Installation and Unit Tests

mvn clean install

## Cobertura

mvn cobertura:cobertura

## Usage

spring-boot:run -Drun.arguments=/path/files

/path/files = folder where the human language files are.

These files will be loaded during the application startup.

Application address: http://localhost:8080
