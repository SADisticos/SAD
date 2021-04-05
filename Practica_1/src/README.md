# SAD

Exercicis per la P1:

- 1a versió: sense MVC, parser char-a-char. 3 classes: Line, EditableBufferedReader, TestReadLine
- 2a versió amb MVC

Opcionals:

- Calculadora
- Suport d'edició de la API amb edició multilínea

Exercicis resolts (Practica_1/src/):

- basic: parser char a char d'una única línia amb seqüències d'escape sense mvc, linia amb StringBuilder
- basicScanner: parser char a char amb la classe Scanner d'úna única línia

Exercicis per resoldre (Practica_1/src/):
- mvc: edició del mode 'basic' per incorporar el patró Model-View-Controller amb Observer/Observable fent servir una nova clase Console (vista)
- mvcProperty: millora del mvc fent servir PropertyChange (Listener y Events) que s'utilitzen actualment en java
- multiline: parser char a char amb edició multilínia (incorpora el moviment amunt i avall)
- calculator: projecte de formulari bàsic com a calculadora bàsica de sencers




Per què no utilitzar Scanner? </br>
Scanner està pensada per ser utilitzada en arxius de texte i no lectura de lletres en temps real com si és el cas de read(). De fet, per poder introduir qualsevol dada s'ha d'utilitzar '\n'
