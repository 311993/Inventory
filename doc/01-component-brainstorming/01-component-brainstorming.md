# Portfolio Part 1: Component Brainstorming

- **Name**: David Stuckey
- **Dot Number**: stuckey.116
- **Due Date**: 09/16 @ 3:00 PM

## Assignment Overview

The overall goal of the portfolio project is to have you design and implement
your own OSU component. There are no limits to what you choose to design and
implement, but your component must fit within the constraints of our software
sequence discipline. In other words, the component must extend from Standard and
must include both a kernel and a secondary interface.

Because this is a daunting project, we will be providing you with a series of
activities to aid in your design decisions. For example, the point of this
assignment is to help you brainstorm a few possible components and get some
feedback. For each of these components, you will need to specify the high-level
design in terms of the software sequence discipline. In other words, you will
describe a component, select a few kernel methods for your component, and select
a few secondary methods to layer on top of your kernel methods.

You are not required to specify contracts at this time. However, you are welcome
to be as detailed as you'd like. More detail means you will be able to get more
detailed feedback, which may help you decide which component to ultimately
implement.

## Assignment Checklist

To be sure you have completed everything on this assignment, we have littered
this document with TODO comments. You can browse all of them in VSCode by
opening the TODOs window from the sidebar. The icon looks like a tree and will
likely have a large number next to it indicating the number of TODOS. You'll
chip away at that number over the course of the semester. However, if you'd
like to remove this number, you can disable it by removing the following
line from the `settings.json` file:

```json
"todo-tree.general.showActivityBarBadge": true,
```

Which is not to be confused with the following setting that adds the counts
to the tree diagram (you may remove this one as well):

```json
"todo-tree.tree.showCountsInTree": true,
```

## Assignment Learning Objectives

Without learning objectives, there really is no clear reason why a particular
assessment or activity exists. Therefore, to be completely transparent, here is
what we're hoping you will learn through this particular aspect of the portfolio
project. Specifically, students should be able to:

1. Integrate their areas of interest in their personal lives and/or careers with
   their knowledge of software design
2. Determine the achievablility of a software design given time constraints
3. Design high-level software components following the software sequence
   discipline

## Assignment Rubric: 10 Points

Again, to be completely transparent, most of the portfolio project, except the
final submission, is designed as a formative assessment. Formative assessments
are meant to provide ongoing feedback in the learning process. Therefore,
the rubric is designed to assess the learning objectives *directly* in a way
that is low stakes—meaning you shouldn't have to worry about the grade. Just
do good work.

1. (3 points) Each design must align with your personal values and long-term
   goals. Because the goal of this project is to help your build out a
   portfolio, you really ought to care about what you're designing. We'll give
   you a chance to share your personal values, interests, and long-term goals
   below.
2. (3 points) Each design must be achievable over the course of a single
   semester. Don't be afraid to design something very small. There is no shame
   in keeping it simple.
3. (4 points) Each design must fit within the software sequence discipline. In
   other words, your design should expect to inherit from Standard, and it
   should contain both kernel and secondary methods. Also, null and aliasing
   must be avoided, when possible. The methods themselves must also be in
   justifiable locations, such as kernel or secondary.

## Pre-Assignment

> Before you jump in, we want you to take a moment to share your interests
> below. Use this space to talk about your career goals as well as your personal
> hobbies. These will help you clarify your values before you start
> brainstorming. Plus it helps us get to know you better! Feel free to share
> images in this section.

  My hobbies include reading, worldbuilding, game development, video games, and
  tabletop games. My academic interests involve computer graphics, algebraic structures,
  and modeling/simulation. My career goal is to work in game development.

## Assignment

As previously stated, you are tasked with brainstorming 3 possible components.
To aid you in this process, we have provided [some example components][example-components]
that may help you in your brainstorming. All of these components were made at
some point by one of your peers, so you should feel confident that you can
accomplish any of them.

There is no requirement that you use any of the components listed above.
If you want to model something else, go for it! Very common early object
projects usually attempt to model real-world systems like banks, cars,
etc. Make of this whatever seems interesting to you, and keep in mind that
you're just brainstorming right now. You do not have to commit to anything.

### Example Component

To help you brainstorm a few components, we've provided an example below of a
component you already know well: NaturalNumber. We highly recommend that you
mirror the formatting as close as possible in your designs. By following this
format, we can be more confident that your designs will be possible.

- Example Component: `NaturalNumber`
  - **Description**:
    - The purpose of this component is to model a non-negative
      integer. Our intent with this design was to keep a simple kernel that
      provides the minimum functionality needed to represent a natural number.
      Then, we provide more complex mathematical operations in the secondary
      interface.
  - **Kernel Methods**:
    - `void multiplyBy10(int k)`: multiplies `this` by 10 and adds `k`
    - `int divideBy10()`: divides `this` by 10 and reports the remainder
    - `boolean isZero()`: reports whether `this` is zero
  - **Secondary Methods**:
    - `void add(NaturalNumber n)`: adds `n` to `this`
    - `void subtract(NaturalNumber n)`: subtracts `n` from `this`
    - `void multiply(NaturalNumber n)`: multiplies `this` by `n`
    - `NaturalNumber divide(NaturalNumber n)`: divides `this` by `n`, returning
      the remainder
    - ...
  - **Additional Considerations** (*note*: "I don't know" is an acceptable
    answer for each of the following questions):
    - Would this component be mutable? Answer and explain:
      - Yes, basically all OSU components have to be mutable as long as they
        inherit from Standard. `clear`, `newInstance`, and `transferFrom` all
        mutate `this`.
    - Would this component rely on any internal classes (e.g., `Map.Pair`)?
      Answer and explain:
        - No. All methods work with integers or other NaturalNumbers.
    - Would this component need any enums or constants (e.g.,
      `Program.Instruction`)? Answer and explain:
        - Yes. NaturalNumber is base 10, and we track that in a constant called
          `RADIX`.
    - Can you implement your secondary methods using your kernel methods?
      Answer, explain, and give at least one example:
      - Yes. The kernel methods `multiplyBy10` and `divideBy10` can be used to
        manipulate our natural number as needed. For example, to implement
        `increment`, we can trim the last digit off with `divideBy10`, add 1 to
        it, verify that the digit hasn't overflown, and multiply the digit back.
        If the digit overflows, we reset it to zero and recursively call
        `increment`.

Keep in mind that the general idea when putting together these layered designs
is to put the minimal implementation in the kernel. In this case, the kernel is
only responsible for manipulating a digit at a time in the number. The secondary
methods use these manipulations to perform more complex operations like
adding two numbers together.

Also, keep in mind that we don't know the underlying implementation. It would be
completely reasonable to create a `NaturalNumber1L` class which layers the
kernel on top of the existing `BigInteger` class in Java. It would also be
reasonable to implement `NaturalNumber2` on top of `String` as seen in
Project 2. Do not worry about your implementations at this time.

On top of everything above, there is no expectation that you have a perfect
design. Part of the goal of this project is to have you actually use your
component once it's implemented to do something interesting. At which point, you
will likely refine your design to make your implementation easier to use.

### Component Designs

> Please use this section to share your designs.

- Component Design #1: Matrix
  - **Description**:
    - The purpose of this component is to model operations on a real-valued mathematical matrix.
    The kernel allows the matrix to operate as a 2D array and the secondary methods
    add a variety of linear algebra operations.
  - **Kernel Methods**:
    - `void setEntry(int r, int c, double n)`: sets the entry at row `r` and column `c` to `n`
    - `double getEntry(r, c)`: returns the entry at row `r` and column `c`
    - `int[] size()`: returns the size of the matrix in the format [rows, columns]
  - **Secondary Methods**:
    - `void add(Matrix m)`: adds the Matrix `m` to `this` (requires compatible dimensions)
    - `void multScalar(double a)`: multiplies `this` by the scalar `a`
    - `void multMatrix(Matrix m)`: multiplies `this` by the Matrix `m` (requires compatible dimensions)
    - `void rowSwap(int r1, int r2)`: swaps rows `r1` and `r2` of `this`
    - `void rowAdd(int b, int d)`: adds row `b` of  `this` to row `d` of `this`
    - `void rowMult(int r, double a)`: multiplies row `r` of `this` by the scalar `a`
    - `void rref()`: reduces `this` to row-echelon form
    - `boolean hasZeroRow()`: returns whether `this` contains a row of all zeroes
    - `double[] getRow(int r)`: returns row `r` of `this` as a double[]
    - `double[] getColumn(int c)`: returns column `c` of `this` as a double[]
    - `void setRow(int r, double[] vals)`: sets row `r` of `this` to the entries in vals
    - `void setColumn(int c, double[] vals)`: sets column `c` of `this` to the entries in vals
    - `Matrix transpose()`: returns the transpose of `this`
    - `static Matrix identity(int n)`: returns the `n` x `n` identity matrix
    - `double det()`: returns the determinant of `this`
  - **Additional Considerations** (*note*: "I don't know" is an acceptable
    answer for each of the following questions):
    - Would this component be mutable? Answer and explain:
      - Yes. The methods in Standard (`clear`,`transferFrom`, and `clear`)
      require mutability. Furthermore a non-mutable matrix would limit the
      linear algebra operations that could be performed.
    - Would this component rely on any internal classes (e.g., `Map.Pair`)?
      Answer and explain:
      - Probably not. All matrix operations will act on matrices or
      primitive types. There is a chance a Vector class for the special case of
      an n x 1 Matrix would be useful.
    - Would this component need any enums or constants (e.g.,
      `Program.Instruction`)? Answer and explain:
      - Yes. The numbers zero and one are needed when performing some of the
      secondary methods e.g. `hasZeroRow` and `identity`. These may not need to
      be declared constants though.
    - Can you implement your secondary methods using your kernel methods?
      Answer, explain, and give at least one example:
      - Yes, `setEntry` and `getEntry` can manipulate any part of the matrix and
      thus can be used to construct the secondary methods. For example, `multScalar`
      can be implemented by iterating `i` from 0 to `size()[0]` and `j` from 0 to `size()[1]`
      and calling `setEntry(i,j, a * getEntry(i,j))`.


- Component Design #2: IndexedColorImage
  - **Description**:
    - The purpose of this component is to model the storage and rendering of an
    image with a limited color palette.
  - **Kernel Methods**:
    - `void setPalette(Color[] pal)`: change the Color palette of the image
    - `Color getColor(int x, int y)`: get the Color at `(x,y)` in the image
    - `void setColor(int x, int y, int color)`: set the Color at `(x,y)` to entry `color`in the palette
    - `int[] size()`: return (width, height)
    - `Color[] getPalette()`: returns the Color palette of the image
  - **Secondary Methods**:
    - `void fillRect(int x, int y, int w, int h, int color)`: sets a rectangle in the image to entry `color` in the palette
    - `IndexedColorImage scale(int scaleFactor)`: returns a scaled copy of `this`
    - `Image render()`: converts `this` to a standard Java Image
    - `void display(Graphics g)`: displays `this` graphically in graphics context g
    - `void rotate(int degrees)`: rotate this by `degrees` (a multiple of 90)
    - `void reflect(boolean horizontal, boolean vertical)`: flip the image horizontally and/or vertically
    - `void paste(IndexedColorImage src, int x, int y)`: copies `src` into `this` at `(x,y)`
    - `void bitmask(IndexedColorImage mask, int x, int y)`: uses `mask` as a bitmask on `this` at `(x,y)`
  - **Additional Considerations** (*note*: "I don't know" is an acceptable
    answer for each of the following questions):
    - Would this component be mutable? Answer and explain:
      - Yes. The methods in Standard (`clear`,`transferFrom`, and `clear`)
      require mutability. Furthermore, the image's pixels and palette need to be modified in place.
    - Would this component rely on any internal classes (e.g., `Map.Pair`)?
      Answer and explain:
      - No, but it could implement a Color class rather than using the standard
      Java one. Otherwise, all operations will deal exclusively with ints,
      standard Java classes, and the class itself.
    - Would this component need any enums or constants (e.g.,
      `Program.Instruction`)? Answer and explain:
      - Probably not. The class doesn't rely on any special values other than 0.
    - Can you implement your secondary methods using your kernel methods?
      Answer, explain, and give at least one example:
      - Yes, `setColor` and `getColor` are sufficient for modifying the pixels of the image,
      and `setPalette` / `getPalette` for modifying the palette. For example, `fillRect`
      can be implemented by iterating i from x to x+w and j from y to y+h and calling `setColor(i,j)`


- Component Design #3: Inventory
  - **Description**:
    - The purpose of this component is to model the movement of items between
    slots in an inventory.
  - **Kernel Methods**:
    - `void addItem(int slot, Item i)`: adds Item `i` to the slot at `slot`
    - `Item removeItem(int slot)`: removes and returns the Item at `slot`
    - `int size()`: returns the number of slots in the inventory'
    - `void restrictIntake(String tag, int[] tagVals)`: restricts the inventory
        to accept only Items with tag <`tag`, `tagVals[any]`>
    - `void freeRestrictions()`: removes restrictions on inventory contents
    - `int hasItem(String item)`: returns the first location of Item with name `item` in the inventory or -1 if not found
  - **Secondary Methods**:
    - `Item getItem(int slot)`: returns the Item at `slot`
    - `void transferItems(Inventory src, int srcSlot, int destSlot)`: swaps the items at `srcSlot` in `src` and `destSlot` in `this`
    - `void swapItems(int src, int dest)`: swaps the items at slots `src` and `dest`
    - `int nextEmptySlot()`: returns the first slot in the inventory with an `EMPTY` item or -1 if none exists
    - `void useItem(int slot)`: consumes an item at `slot`
  - **Additional Considerations** (*note*: "I don't know" is an acceptable
    answer for each of the following questions):
    - Would this component be mutable? Answer and explain:
      - Yes. The methods in Standard (`clear`,`transferFrom`, and `clear`)
      require mutability. Furthermore the primary function of this component:
      movement of internal data, requires mutability.
    - Would this component rely on any internal classes (e.g., `Map.Pair`)?
      Answer and explain:
      - Yes, an `Inventory.Item` class modeling a single item in the inventory.
      This would consist of a String `name`,and a Map<String, int> `tags`.
    - Would this component need any enums or constants (e.g.,
      `Program.Instruction`)? Answer and explain:
      - Yes. A constant `Inventory.EMPTY` representing an empty inventory slot.
    - Can you implement your secondary methods using your kernel methods?
      Answer, explain, and give at least one example:
      - Yes. `addItem` and `removeItem` are sufficient to manipulate the internal
      data of the inventory. For example, `swapItems` can be implemented by holding the values of `removeSlot`
      from both slots, then using `addSlot` to place them in the opposite slots.

## Post-Assignment

The following sections detail everything that you should do once you've
completed the assignment.

### Changelog

At the end of every assignment, you should update the
[CHANGELOG.md](../../CHANGELOG.md) file found in the root of the project folder.
Since this is likely the first time you've done this, we would recommend
browsing the existing file. It includes all of the changes made to the portfolio
project template. When you're ready, you should delete this file and start your
own. Here's what I would expect to see at the minimum:

```markdown
# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Calendar Versioning](https://calver.org/) of
the following form: YYYY.0M.0D.

## YYYY.MM.DD

### Added

- Designed a <!-- insert name of component 1 here --> component
- Designed a <!-- insert name of component 2 here --> component
- Designed a <!-- insert name of component 3 here --> component
```

Here `YYYY.MM.DD` would be the date of your submission, such as 2024.04.21.

You may notice that things are nicely linked in the root CHANGELOG. If you'd
like to accomplish that, you will need to make GitHub releases after each pull
request merge (or at least tag your commits). This is not required.

In the future, the CHANGELOG will be used to document changes in your
designs, so we can gauge your progress. Please keep it updated at each stage
of development.

### Submission

If you have completed the assignment using this template, we recommend that
you convert it to a PDF before submission. If you're not sure how, check out
this [Markdown to PDF guide][markdown-to-pdf-guide]. However, PDFs should be
created for you automatically every time you save, so just double check that
all your work is there before submitting. For future assignments, you will
just be submitting a link to a pull request. This will be the only time
you have to submit any PDFs.

### Peer Review

Following the completion of this assignment, you will be assigned three
students' component brainstorming assignments for review. Your job during the
peer review process is to help your peers flesh out their designs. Specifically,
you should be helping them determine which of their designs would be most
practical to complete this semester. When reviewing your peers' assignments,
please treat them with respect. Note also that we can see your comments, which
could help your case if you're looking to become a grader. Ultimately, we
recommend using the following feedback rubric to ensure that your feedback is
both helpful and respectful (you may want to render the markdown as HTML or a
PDF to read this rubric as a table).

| Criteria of Constructive Feedback | Missing                                                                                                                           | Developing                                                                                                                                                                                                                                | Meeting                                                                                                                                                               |
| --------------------------------- | --------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Specific                          | All feedback is general (not specific)                                                                                            | Some (but not all) feedback is specific and some examples may be provided.                                                                                                                                                                | All feedback is specific, with examples provided where possible                                                                                                       |
| Actionable                        | None of the feedback provides actionable items or suggestions for improvement                                                     | Some feedback provides suggestions for improvement, while some do not                                                                                                                                                                     | All (or nearly all) feedback is actionable; most criticisms are followed by suggestions for improvement                                                               |
| Prioritized                       | Feedback provides only major or minor concerns, but not both. Major and minar concerns are not labeled or feedback is unorganized | Feedback provides both major and minor concerns, but it is not clear which is which and/or the feedback is not as well organized as it could be                                                                                           | Feedback clearly labels major and minor concerns. Feedback is organized in a way that allows the reader to easily understand which points to prioritize in a revision |
| Balanced                          | Feedback describes either strengths or areas of improvement, but not both                                                         | Feedback describes both strengths and areas for improvement, but it is more heavily weighted towards one or the other, and/or descusses both but does not clearly identify which part of the feedback is a strength/area for improvement  | Feedback provides balanced discussion of the document's strengths and areas for improvement. It is clear which piece of feedback is which                             |
| Tactful                           | Overall tone and language are not appropriate (e.g., not considerate, could be interpreted as personal criticism or attack)       | Overall feedback tone and language are general positive, tactul, and non-threatening, but one or more feedback comments could be interpretted as not tactful and/or feedback leans toward personal criticism, not focused on the document | Feedback tone and language are positive, tactful, and non-threatening. Feedback addesses the document, not the writer                                                 |

### Assignment Feedback

If you'd like to give feedback for this assignment (or any assignment, really),
make use of [this survey][survey]. Your feedback helps make assignments
better for future students.

[example-components]: https://therenegadecoder.com/code/the-never-ending-list-of-small-programming-project-ideas/
[markdown-to-pdf-guide]: https://therenegadecoder.com/blog/how-to-convert-markdown-to-a-pdf-3-quick-solutions/
[survey]: https://forms.gle/dumXHo6A4Enucdkq9
