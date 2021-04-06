# SAD

Exercicis per la P1:

- 1a versió: sense MVC, parser char-a-char. 3 classes: Line, EditableBufferedReader, TestReadLine (Done)
- 2a versió amb MVC (Done)

Opcionals:

- Calculadora
- Suport d'edició de la API amb edició multilínea

Exercicis resolts (Practica_1/src/):

- basic: parser char a char d'una única línia amb seqüències d'escape sense mvc, linia amb StringBuilder
- basicScanner: parser char a char amb la classe Scanner d'úna única línia
- mvc: edició del mode 'basic' per incorporar el patró Model-View-Controller amb Observer/Observable fent servir una nova clase Console (vista)

Exercicis per resoldre (Practica_1/src/):
- multiline: parser char a char amb edició multilínia (incorpora el moviment amunt i avall)
- calculator: projecte de formulari bàsic com a calculadora bàsica de sencers
- mvcProperty: millora del mvc fent servir PropertyChange (Listener y Events) que s'utilitzen actualment en java

</br> </br>
Per què no utilitzar Scanner? </br>
Scanner està pensada per ser utilitzada en arxius de texte i no lectura de lletres en temps real com si és el cas de read(). De fet, per poder introduir qualsevol dada s'ha d'utilitzar '\n'

Info interessant:
- https://programacion.net/articulo/beans_basico_110/9
- https://es.wikipedia.org/wiki/JavaBean
- https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/beans/PropertyChangeSupport.html
- https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&ved=2ahUKEwiktcnH5efvAhWxaRUIHXa-BJUQFjABegQIBRAD&url=https%3A%2F%2Felvex.ugr.es%2Fdecsai%2Fjava%2Fpdf%2FC3-serializable.pdf&usg=AOvVaw3scSSKEHYovMcg42Gt9mJI
- https://www.baeldung.com/trie-java
