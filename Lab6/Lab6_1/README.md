# Lab 6_1

## Exercise 1.f and 1.g

##### The Project that I tested was the my second exercise from the first Lab. It passed the defined quality game but was not perfect. The tests covered around 75% of the code, and the parts that were not covered did not need to be.

|       Issue        |                     Problem Description                      |                         How to solve                         |
| :----------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|  Security HotSpot  | `java.util.Random` provides only pseudorandom number generator, and therefore it should not be used for security Reasons. | Instead of using `java.util.Random`, making use of `java.security.SecureRandom` |
| Code smell (major) |         Loop counter Assignment within the Loop Body         | Simple Refactor, changed the **for** to and **while** and declared de variables outside of the while loop. |
| Code smell (major) |             Invoke method(s) only conditionally.             | I really didnt undersant the problem, so I could not solve it. |
| Code smell (minor) | Remove this unused import 'java.security.NoSuchAlgorithmException'. |                   Just remove the import.                    |
| Code smell (minor) | Reorder the modifiers to comply with the Java Language Specification. |         Just chane **public** and **static** order.          |
| Code smell (Info)  |                Remove this 'public' modifier.                |           Just do as the problem description says.           |
| Code smell (minor) | Replace the type specification in this constructor call with the diamond operator ("<>"). |           Just do as the problem description says.           |
| Code smell (minor) | The return type of this method should be an interface such as "List" rather than the implementation "ArrayList". |           Just do as the problem description says.           |

![](/Users/paulopinto/Desktop/TQS_103234/Lab6/Lab6_1/Screenshot 2023-03-29 at 19.15.06.png)

![](/Users/paulopinto/Desktop/TQS_103234/Lab6/Lab6_1/Screenshot 2023-03-30 at 16.29.32.png)

## Exercise 2

O valor de 1 hora e 28 minutos (presente na print) refere-se tipicamente à quantidade de tempo estimada para corrigir a **techical debt** identificada no código-fonte.

A **Techical debt** refere-se ao custo acumulado de manter um código-fonte que foi desenvolvido com práticas sub-ótimas ou atalhos. Esta representa a quantidade de trabalho necessária para corrigir problemas no código-fonte, como bugs, **code smells** e **Security Hotspots**, ajudando a melhorar a qualidade geral do software.

![](/Users/paulopinto/Desktop/TQS_103234/Lab6/Lab6_2/Screenshot 2023-03-30 at 16.57.59.png)

|         Issue         |          Problem Description          |                         How to solve                         |
| :-------------------: | :-----------------------------------: | :----------------------------------------------------------: |
| Code smell (Critical) | Remove usage of generic wildcard type | Since I always returned the same type of object I changed the **?** symbol to the Object Name. |
|  Code smell (major)   | Remove usage of generic wildcard type | Simple Refactor, changed the **for** to and **while** and declared de variables outside of the while loop. |

Depois de corrigir os problemas presentes nesta tabela o valor reduziu para 28 minutos, ou seja uma redução de 1 hora. Alguns dos erros presentes não tinham a ver diretamente com o código mas sim com ficheiros criados com o projeto maven e nunca utilizados, sendo necessário apagar estes.

![](/Users/paulopinto/Desktop/TQS_103234/Lab6/Lab6_2/Screenshot 2023-03-30 at 17.14.31.png)

The Jacoco did not work for some reason that I can't figure out, so for now I can't do this part of the exercise.



## Exercise 3



