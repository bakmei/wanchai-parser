# WanChai Parser
A light weight and easy to use command line parser library for Java developers.

# As Easy as This Example

### Reverse a sentense.

##### Get a handle of the parser
```
CLIParser parser = new DefaultCLIParser( this.getClass() );
```
##### or
```
CLIParser parser = new DefaultCLIParser(new String[] { this.getClass().getPackage().getName() });
```
##### Parse input from command line
```
WordCommand obj = (WordCommand) parser.parse("SHOWME --reverse=true you are How".split(" "));
```
##### Verify the result
```
Assert.assertEquals("How are you", obj.execute());
```
##### Define how the command works
```
@CLICommandTag("SHOWME")
public class WordCommand {
	
	@CLIParamTag
	private String[] words;
	
	@CLIOptionTag("--reverse")
	private Boolean isReverse = false;
  
  public String execute() {
     if (isReverse) {
        return reverse(wordds);
     }
     ...
  }
}
```

# Design Overview

Each input command from the user is very specific, it contains the command to be executed, the zero or more parameters for the execution and some user options.  Since the parameters and the options are specific, the traditional way of handling parameters may not be sufficient to support complex scenarios.  For Linux commands, it is usually specific to a topic such as tar, the parameters are files and directories.  However, a more complex application could have different commands integrated together.  Therefore, mapping each command to a class would make more sense.

From bottom up, this parser is taken into consideration that organizes all the metadata in one place.  I.e. The definitions of commands and specific parameters and options.  By leveraging Java annotation, it is as simple as to put metadata next to the data fields so that changing and maintaining the code becomes more fun!

All commands provide brief help instruction, there is no difference that this parser also does it.  The help command is basically a part of the default implementation that is annotated as default command.  Any user input that doesn't match with any defined commands will fall back to this command.

The design has two types of models: the metadata and the token.  The metadata basically represents the data structure of all the command class which contains relationships to the option and parameter fields.  The token model is the runtime model that contains the input tokens and the associated metadata.  The parser will make use of the token model to populate the user input into the corresponding option and parameter fields.

Pros:
- Fast and slim size.
- No dependencies to the other libraries.
- Use the annotation approach for defining metadata, simple and organized.

Cons:
- No List or Set fields are supported, only array is supported.
- No common options supported.


# Q&A

Why Wan Chai?
It is my favorite place.  Wan Chai is a metropolitan area situated at the western part of the Wan Chai District on the northern shore of Hong Kong Island.

What does the parser do?
It translates user input from command line to some logical units, which is convenient for the developers to invoke the corresponding operations.

How the command, options and parameters are mapped?
A command is mapped to a class, options are mapped one-to-one member field, and parameters are mapped to either a sequence of fields or an array.

How to define the mapping?
Use annotation @CLICommandTag to tag a class as command, use @CLIOptionTag or @CLIParamTag to tag member field(s) as options or parameters.


# Assumptions

1. The annotated option or parameter field should provide a java.lang.String constructor for the parser to construct and insert value.
2. The option can only be in two forms: inclusive or exclusive.  Inclusive example: "-option1=<value>"  or Exclusive example "-option1 <value>"


# Authors

- Raymond Tsang
- Steven Liang
