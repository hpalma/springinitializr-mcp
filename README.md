# Spring Initializr MCP Server

An MCP (Model Context Protocol) server that provides access to Spring Initializr functionality, allowing AI assistants to generate and download Spring Boot projects programmatically.

## What is this?

This project implements an MCP server that wraps the Spring Initializr API, enabling AI assistants (like Claude) to create Spring Boot projects with custom configurations. Instead of manually visiting [start.spring.io](https://start.spring.io), you can now ask your AI assistant to generate projects with specific dependencies, Java versions, and project structures.

## Features

- **Generate Spring Boot Projects**: Create new Spring Boot projects with custom configurations
- **Flexible Configuration**: Support for different project types (Maven/Gradle), languages (Java/Kotlin/Groovy), and Java versions
- **Dependency Management**: Add popular Spring Boot dependencies automatically
- **Native Compilation**: Fast startup times with GraalVM native compilation
- **Cross-Platform**: Native binaries available for Linux, Windows, and macOS (x64 and ARM64)

## Supported Parameters

When generating a project, you can specify:

- **Project Type**: `maven-project`, `gradle-project`, `gradle-project-kotlin`
- **Language**: `java`, `kotlin`, `groovy`
- **Java Version**: `17`, `21`, `24` (defaults to `17`)
- **Packaging**: `jar`, `war` (defaults to `jar`)
- **Dependencies**: Hundreds of Spring Boot starters and dependencies
- **Project Metadata**: Group ID, Artifact ID, name, description, package name

## Installation

### Download Pre-built Binaries

Download the appropriate binary for your platform from the [Releases](https://github.com/yourusername/springinitializr-mcp/releases) page:

- **Linux x64**: `springinitializr-mcp-linux-x64`
- **Windows x64**: `springinitializr-mcp-windows-x64.exe`
- **macOS x64**: `springinitializr-mcp-macos-x64`
- **macOS ARM64**: `springinitializr-mcp-macos-arm64`

### Build from Source

Requirements:
- Java 24
- GraalVM (for native compilation)

```bash
git clone https://github.com/yourusername/springinitializr-mcp.git
cd springinitializr-mcp
./gradlew build
./gradlew nativeCompile
```

## Usage with Claude Desktop

Add the MCP server to your Claude Desktop configuration:

### macOS/Linux
Edit `~/Library/Application Support/Claude/claude_desktop_config.json`:

```json
{
  "mcpServers": {
    "springinitializr": {
      "command": "/path/to/springinitializr-mcp-binary"
    }
  }
}
```

### Windows
Edit `%APPDATA%/Claude/claude_desktop_config.json`:

```json
{
  "mcpServers": {
    "springinitializr": {
      "command": "C:\\path\\to\\springinitializr-mcp-windows-x64.exe"
    }
  }
}
```

## Example Usage

Once configured, you can ask Claude to generate Spring Boot projects:

> "Create a Spring Boot web application with Spring Data JPA, PostgreSQL, and Spring Security dependencies"

> "Generate a Kotlin Spring Boot project using Gradle with WebFlux and MongoDB"

> "Create a Maven-based Spring Boot project with Thymeleaf, Validation, and Actuator"

The tool will generate and download the project as a ZIP file to your specified location.

## Available Dependencies

The server supports all Spring Initializr dependencies, including:

- **Web**: Spring Web, WebFlux, GraphQL, REST repositories
- **Security**: Spring Security, OAuth2, LDAP
- **Data**: JPA, JDBC, MongoDB, Redis, Elasticsearch, R2DBC
- **Messaging**: RabbitMQ, Apache Kafka, WebSocket
- **Cloud**: Spring Cloud Gateway, Config, Eureka, Feign
- **Ops**: Actuator, Micrometer, Distributed Tracing
- **AI**: Spring AI with various model providers
- **Testing**: TestContainers, Contract testing

For a complete list, see the generated constants or check [Spring Initializr](https://start.spring.io).

## Development

### Running in Development

```bash
# Run with dev profile for more logging
./gradlew bootRun --args='--spring.profiles.active=dev'

# Generate updated constants from Spring Initializr
./gradlew generateToolDescriptions

# Run tests
./gradlew test

# Run native tests
./gradlew nativeTest
```

### Code Generation

The project uses a Gradle task to fetch the latest Spring Initializr metadata and generate constants for supported dependencies and Java versions:

```bash
./gradlew generateToolDescriptions
```

This ensures the tool always supports the latest Spring Boot versions and dependencies.

## Testing

The project includes comprehensive tests:

- **Unit Tests**: Standard JUnit tests for core functionality
- **Integration Tests**: Tests for the MCP protocol implementation
- **Native Tests**: Validation that native compilation works correctly

```bash
# Run all tests
./gradlew test nativeTest nativeIntegrationTest
```

## CI/CD

The project uses GitHub Actions for:

- **Continuous Integration**: Build and test on every push/PR
- **Release Automation**: Create native binaries for all platforms
- **Multi-platform Support**: Linux, Windows, macOS (x64 and ARM64)

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass (`./gradlew test nativeTest`)
6. Commit your changes (`git commit -m 'Add amazing feature'`)
7. Push to the branch (`git push origin feature/amazing-feature`)
8. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Spring Initializr](https://start.spring.io) - The underlying service for generating Spring Boot projects
- [Model Context Protocol](https://modelcontextprotocol.io) - The protocol specification
- [Spring AI](https://spring.io/projects/spring-ai) - MCP server framework support
- [GraalVM](https://www.graalvm.org) - Native compilation capabilities