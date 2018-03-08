# blowling_kata

The bowling project reads a file with the score of a bowling match and returns the final score.
The file must have the following format:

| 1 4 | 4 5 | 6 / | 5 / | X | 0 1 | 7 / | 6 / | X | 2 / 6 |

Where "/" represents a split and "X" a strike.

It will only read the first line and no format check is performed.

## Usage

To use it copy the "bowling_kata-V1-standalone.jar" file from the target/uberjar directory and run it followed by the path of the input file

You can also clone the repository and run the project using [leinigen](https://leiningen.org/).

## Example

```
$ cat input.txt
| 1 4 | 4 5 | 6 / | 5 / | X | 0 1 | 7 / | 6 / | X | 2 / 6 |
$ java -jar bowling_kata-V1-standalone.jar "input.txt"
133
```
