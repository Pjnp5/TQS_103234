# Lab 6_1

## Exercise 1.f and 1.g

##### The Project that I tested was the my second exercise from the first Lab. It passed the defined quality game but was not perfect. The tests covered around 75% of the code, and the parts that were not covered did not need to be.

|       Issue        |                     Problem Description                      |                         How to solve                         |
| :----------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|  Security HotSpot  | `java.util.Random` provides only pseudorandom number generator, and therefore it should not be used for security Reasons. | Instead of using `java.util.Random`, making use of `java.security.SecureRandom` |
| Code smell (major) |         Loop counter Assignment within the Loop Body         | Simple Refactor, t changed the **for** to and **while** and declared de variables outside of the while loop. |
| Code smell (major) |             Invoke method(s) only conditionally.             | I really didnt undersant the problem, so I could not solve it. |
| Code smell (minor) | Remove this unused import 'java.security.NoSuchAlgorithmException'. |                   Just remove the import.                    |
| Code smell (minor) | Reorder the modifiers to comply with the Java Language Specification. |         Just chane **public** and **static** order.          |
| Code smell (Info)  |                Remove this 'public' modifier.                |           Just do as the problem description says.           |
| Code smell (minor) | Replace the type specification in this constructor call with the diamond operator ("<>"). |           Just do as the problem description says.           |
| Code smell (minor) | The return type of this method should be an interface such as "List" rather than the implementation "ArrayList". |           Just do as the problem description says.           |

![](/Users/paulopinto/Desktop/TQS_103234/Lab6/Lab6_1/Screenshot 2023-03-29 at 19.15.06.png)

![](/Users/paulopinto/Desktop/TQS_103234/Lab6/Lab6_1/Screenshot 2023-03-30 at 16.29.32.png)

## Exercise 2
