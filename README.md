# SOLID Principles Practice Exercise

This directory contains practice exercises to help you apply SOLID principles in different programming languages.

## Exercise: Refactor OrderProcessor

The `OrderProcessor` class violates multiple SOLID principles. Your task is to refactor it to follow all five SOLID principles:

- **S**ingle Responsibility Principle
- **O**pen/Closed Principle
- **L**iskov Substitution Principle
- **I**nterface Segregation Principle
- **D**ependency Inversion Principle

## Files

- `OrderProcessor.cs` - C# implementation
- `order_processor.py` - Python implementation
- `OrderProcessor.ts` - TypeScript implementation

## Instructions

1. Choose your preferred language
2. Review the code in the corresponding file
3. Identify which SOLID principles are being violated
4. Refactor the code to follow all SOLID principles
5. Consider how each principle applies to this example

## What to Refactor

The current `OrderProcessor` class:
- Handles multiple responsibilities (validation, calculation, persistence, notification)
- Uses hard-coded dependencies
- Contains magic strings and numbers
- Is difficult to test and extend

Think about how you can:
- Separate concerns into different classes
- Use dependency injection
- Create abstractions for extensibility
- Make the code more testable

Good luck! 🚀

