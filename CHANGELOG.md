# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Calendar Versioning](https://calver.org/) of
the following form: YYYY.0M.0D.

##[2024.12.03]

### Added
- Readme file for graphical demo.
- Javadoc html files.

### Updated
- InventoryManager now displays tags of items.
- Restructured directories.
- Completed reflection.
- Fixed transferFrom() transferring in wrong direction.

##[2024.11.28]

### Added
- JUnit tests for kernel methods dealing with restrictions
- JUnit tests for secondary methods
- JUnit tests for Standard methods
- InventoryManager terminal demo as a use case.

### Updated
- restrict() method no longer returns empty items, since they are allowed in any Inventory.
- useItem() now works correctly for empty items and will replace the last named item in a slot with an empty item rather than leaving that item with count 0.
- nextPlacement() now correctly enforces max stack parameter.

##[2024.11.28]

### Added
- JUnit tests for kernel methods not dealing with restrictions

### Updated
- addItem() kernel method now merges tags of same-name items being stacked. Previously discarded tags of added item.

##[2024.11.15]

### Added
- Skeleton for command line demo for Inventory component based on the proof of concept demos.
- Functional (though without all intended features) graphical demo for Inventory component.
    - Model including Inventories for graphical demo.
    - View creator for graphical demo.
        - Canvas for graphical demo display.
    -Controller for graphical demo.
- Iterator for Inventory1

### Updated
- visibility of BasicItem changed to public to allow instantiation outside of the package

##[2024.11.13]

### Added
- InventoryKernel implementation : Inventory1
- Method implementations for kernel methods in Inventory1
- Standard method implementations for Inventory1
- Convention and correspondence for Inventory1
- Disallowed hashCode implementation in abstract class

### Updated
- restrict kernel method to remove and return newly disallowed items

##[2024.11.03]

### Added
- Abstract class for Inventory: InventorySecondary
- Basic implementation for Item interface: BasicItem
- Method implementations for Inventory secondary methods
- Method implementations for Item methods
- getTags() method for Item interface

### Updated
- Removed mentions of InventoryConcept in Inventory method signatures
- Fixed swapItems() requires clause to match intended behavior and transerItems() requirements

## [2024.10.14]

### Added
- Interfaces for InventoryKernel, Inventory, and Item.
- `isAllowed(Item)` kernel method to allow client to check an Item against restrictions and to facilitate `addItem` changes below.
- `isAt(int slot, String name)` secondary method header to conveniently check whether an item is at a certain slot.
- Full method contracts for InventoryKernel, Inventory, and Item methods.

### Updated
- Changed kernel method `restrictIntake()` to  `restrict(String)` for brevity and implementation flexibility.
- changed `addItem` kernel method from boolean to void return type and added conditions which would previously cause a return of false to the requires clause.
- Changed `copyItem` and `transferItem` secondary method return types to void and added requires clause conditions in line with the changes to `addItem` above.
-Changed `useItem` secondary method to return the name of the item used.

## [2024.10.02]

### Added
- Added demonstrations of client usage of Inventory proof of concept
- Completed 02-component-proof-of-concept

### Updated
- Fixed bugs in `nextPlacement` and `nextIndexOf` searching
- Added secondary method `copyItem` to Inventory proof of concept

## [2024.09.30]

### Added
- Implemented a proof of concept for an Inventory component
- Implemented a proof of concept Item internal class for Inventory component
- Added a demo of proof of concept's client usage

### Updated
- Changed `hasItem` kernel method to `nextIndexOf` to more accurately reflect its usage
- Changed design to include `transferItem` secondary method
- Changed `transferItems` secondary method to an overload of `swapItems` to more accurately reflect its usage and prevent name confusion with `transferItem`
- Changed design of secondary method `nextEmptySlot` to `nextPlacement` for more versatile usage

## [2024.09.16]

### Added

- Designed a Matrix component
- Designed a IndexedColorImage component
- Designed a Inventory component
- Completed 01-component-brainstorming