# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Calendar Versioning](https://calver.org/) of
the following form: YYYY.0M.0D.

## 2026.02.06

### Added

- Designed a PriorityQueue component
- Designed a FixedOrderPriorityQueue component
- Designed a ExplicitPriorityQueue component

## 2026.02.25

### Added

- PriorityQueueKernel interface
- PriorityQueue interface
- PriorityQueueSecondary abstract class and secondary method implementations
- PriorityQueue1L kernel method implementations for PriorityQueue on Queue

## 2026.03.10

### Added

- diagram.txt diagram to show hierarchy of components
- PriorityQueue2 implementation (PriorityQueue on heap)

## 2026.04.15

### Updated

- Moved clear() to kernel
- Added common methods to secondary

## 2026.04.23

### Added

- Designed test suite for PriorityQueue component
- Designed two different use cases for PriorityQueue
- Added proof-of-concept documentation missing from part 2

### Updated

- Fixed several bugs in PriorityQueue2, PriorityQueueSecondary, and PriorityQueue1L
- Added PriorityQueueDemo featuring integer counting, and a task scheduler

## 2026.04.24

### Updates

- Added missing javadoc from clear() method
