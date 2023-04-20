# Automat Push Down

Automat PushDown pentru gramatica de precedenta simpla. Acest automat va specifica daca un sir introdus de la tastatura reprezinta o expresie aritmetica corecta.

Exemplu:

a+a-(a*a) -> sirul va fi acceptat

a--(a+) -> eroare, sirul nu va fi acceptat

# Documentatie

Elementele multimii T se numesc terminale.
Elementele multimii N se numesc neterminale.
Perechiile [cheie, valori] din P se numesc reguli de rescriere sau productii.

Pe baza productiilor se genereaza o matrice de precedenta ce include simboluri (<., >., =., acc) prin care se stabilesc actiunile. 

<. si =. sunt folosite pentru actiunea de deplasare. Deplasarea preia primul caracter din sirul de intrare si il va pune la finalul sirului din varful stivei de comparare.

\>. este folosit pentru actiunea de reducere. In acest caz, se verifica cea mai lunga derivare din sirul din varful stivei de comparare, incepand de la dreapta spre stanga (se verifica care este pivotul). Pivotul va fi cautat in setul de valori din productii si daca este gasit, va fi schimbat cu cheia corespunzatoare.

Daca in matrice se va intalni actiunea "acc", inseamna ca sirul de la intrare este acceptat, adica sirul reprezinta o expresie aritmetica corecta.

Daca in parcurgerea matricii de precedenta se afla '0', dupa compararea sirului de intrare cu stiva de comparare, inseamna ca sirul introdus nu poate reprezenta o expresie aritmetica corecta.

# Pre-conditii
Sirul ce va fi introdus de la tastatura va trebui sa se termine cu caracterul '$'. 

Exemplu: a+a-(a*a)$

Pentru a simboliza termenii unei expresii se va folosi caracterul 'a', precum am aratat in exemplul de mai sus.

Operatiile posibile de testat sunt specificate in multimea T din fisierul input. Prin urmare, sirul de intrare trebuie sa contina doar operatiile disponibile in multimea T. 

In multimea T se pot adauga noi operatii, dar pentru o corecta functionare a automatului, va fi necesara adaugarea unor reguli privind noua operatie in multimea P (din fisierul input).
