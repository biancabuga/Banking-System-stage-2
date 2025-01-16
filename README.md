# Proiect_2_poo
### **Proiect etapa 2 - Buga Bianca Gabriela 321CD**
Acest proiect simuleaza un sistem bancar conceput pentru 
gestionare utilizatorilor, conturilor si tranzactiilor financiare.Proiectul
permite extensibilitatea, acesta incluzand 4 design pattern-uri : 
Builder, Factory, Singleton si Strategy.

---

## **Structura proiectului**
- User – Gestionează utilizatorii și conturile acestora.
- Account – Reprezintă conturile bancare și operațiunile aferente.
- Card – Gestionează informațiile și statusul cardurilor.
- Transaction – Modelează tranzacțiile financiare.
- Converter – Asigură conversia valutară.
- Main – Punctul de intrare în aplicație, gestionează logica principală.
- Commerciant - Clasa pentru commerciantii care iau parte la o tranzactie.
- BusinessAccount - Clasa pentru conturile de tip Business care mosteneste clasa Account.
- ClassicAccount - Clasa pentru conturile de tip Classic care mosteneste clasa Account.
- AccountFactory - Clasa care implementeaza design patternul Factory pentru conturi.
- NumberOfTransactions/SpendingThresholdStrategy - Clasa care implementeaza logica pentru calcularea cashbackului de la comerciantii care au aceasta strategie.
- CashbackStrategy - interfata pentru clasele anterioare.
- CashbackContext - clasa care fixeaza tipul de strategie corespunzator.
- Equalsplit - clasa pentru comanda splitPayment de tip equal; in ea salvez datele comenzii la apelare, pentru a le putea folosi dupa ce accepta toti participantii.
- SplitPayment - acelasi lucru ca EqualSplit, doar ca pentru splitPayment de tip custom.


# **Functionalitati adaugate**
1. **Plan** :
- o noua instanta plan pentru User
- am realizat si o functie de upgrade care face trecerea de la un plan la altul prin plata unei taxe
- am aplicat tipurile de comision corespunzatoare fiecarui plan.

2. **Cashback** :
- am creat doua strategii de cashback, una care se bazeaza pe cat ai cheltuit in total la un comerciant,
iar cealalta se bazeaza pe numarul de tranzactii facute pentru un comerciant.

3. **WithdrawSavings** :
- am realizat o functie care extrage bani dintr-un cont de economii ai unui user si ii transfera
intr-un alt cont al aceluiasi user, dar care este de tip Classic.

4. **CashWithdrawal** :
- o comanda prin care putem retrage bani de la bancomat
- se ia comision in functie de planul utilizatorului

5. **Conversia valutara** :
- asigura conversia intre monede folosind cursuri valutare
predefinite
- conversia se realizeaza prin intermediul unui graf care
foloseste algoritmul de cautare in adancime pentru a gasi drumul
intre 2 noduri oarecare

6. **Custom Split Payment** :
- am creat 2 clase care reprezinta cele 2 tipuri de comenzi splitPayment : custom si equal
- de fiecare data cand este apelata la input comanda splitPayment o salvez intr-un obiect, 
in functie de ce tip este.
- de fiecare data cand este apelata comanda acceptSplitPayment, setez o instanta a
contului implicat la "yes"
- verific de fiecare data daca numarul de conturi care au instanta acceptance egala cu yes
este egal cu numarul de conturi implicate in comanda splitPayment.
- daca da pot trece mai departe si verific ce fel de comanda splitPayment am si in functie de
acest lucru inregistrez tranzactia.
