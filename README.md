1. Greater Bank Coding Challenge

This repository contains  source code for the Greater Bank Coding Challenge.

1.1 Scope

This prototype maintains customer account balances based on incoming transaction data.
This is a standalone application or prototype, But how ever it can be extended or integrated 
to an existing account management or banking system.

1.2 Design rationale

From a high level perspective, the application could be designed in two ways:

- As a long-running daemon process that contains its own scheduling mechanism;
	-- we can use Spring Scheduler using Cron expressions with Database like Oracle and MySql
- As a one-shot program that is triggered by some external scheduler .

It has been decided to choose the former scheme, as it is more suited to a
self-contained application design and will be simpler to deploy. However, the 
system should be decoupled such that it can be easily reconfigured to use the 
latter scheme.

1.2.1 Separation of concerns

In general, the system should have a clear separation of concerns between logical
chunks. The following distinct areas of operation have been identified:

- Scheduling of processing job execution;
- High-level control work-flow with Service layer and manager Layers;
- All operations are handled in service / manager Layer
- Storage of customer accounts and their credit/debit amounts;
- File system related methods (reading/writing/copying files).

1.3 Implementation

This  application has been written in Java 1.8 (as has features like Streams and Lambda expressions)
Its a standard Maven project layout. 
Dependency management is also handled via Maven.

The Spring framework  is used throughout the application.
Spring Boot uses a "convention over configuration" approach to minimise boilerplate code
and allow rapid application development.


1.4 Architecture

For a diagrammatic overview of the application architecture, see the 
class diagram inside docs/GB-Class-Diagram.JPG folder

The main entry point of the application is the AppMain.java class. This class is
annotated as a `SpringBootApplication` which will instruct the Spring framework to 
instantiate its context and wire up any `@Component` classes found on the classpath.

The TransactionProcessScheduler.java responsible for executing scheduled jobs.
It makes use of Spring's scheduling support which makes it very simple.

The scheduler runs at 06:01am and 21:01pm each day. This ensures that 
processing commences within 5 minutes of delivery.

The scheduler delegates delegate the request TransactionService(injected through Spring DI) whhich is responsible to parse the incoming file , prepare report and archive the same.

The `TRANSACTION_PROCESSING` environment variable need to be set in the system.
If this variable not set, the application will not start.

1.5 Exception handling strategy

- AccountProcessException 
	- This Is Custom exception that is thrown when any exception occurred while processing Custom Transactions
- FileProcessingException
	- This Is Custom exception that is thrown when any exception occurred while processing the incoming files
1.6 Performance

The average processing time for a file with 500,000 transactions is 600 ms.
Can be improved by Java 8 by using MappedByteBuffer and FileChannels up to 200ms
	 

1.7 Testing strategy

As a general rule, all methods that contain logic are unit tested. The main processing 
control flow logic is integration tested with the entire application.

