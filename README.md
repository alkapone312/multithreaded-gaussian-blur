
# Raport z Testowania Wydajności Implementacji Konwolucji w Java i C++

## Zadanie
Napisz program, w którym wykorzystane zostanie JNI. Zadanie do wykonania polegać
ma na zadeklarowaniu klasy Java z dwiema metodami służącymi do obliczania dyskretnej
dwuwymiarowej funkcji splotu  (ang. 2D discrete convolution function): natywną oraz
normalną, a następnie przetestowaniu ich działania.

Metoda do obliczania splotu powinna przyjmować na wejście dwie tablice dwuwymiarowe 
(jądro splotu oraz macierz przetwarzaną) oraz produkować na wyjściu wynik (macierz wynikową). 
O tym, czym jest funkcji splotu, poczytać sobie można w wielu tutorialach, np. w dokumencie 
https://staff.uz.zgora.pl/agramack/files/conv/Splot.pdf

Podczas testowania należy wygenerować odpowiednio duży problem obliczeniowy, a potem spróbować
go rozwiązać korzystająć z obu zaimplementowanych metod. Testowanie powinno dać odpowiedź na
pytanie, która z implementacji jest wydajniejsza. Stąd uruchamianiu metod powinno towarzyszyć 
mierzenie czasu ich wykonania (czyli coś na kształt testów wydajnościowych). Sposób wykonania 
takich testów jest dowolny, jednak warto spróbować wykorzystać do tego np. framework Mockito.

Jako wynik zadania, oprócz kodów źródłowych Java oraz kodu zaimplementowanej natywnej biblioteki, 
należy dostarczyć również raport z omówieniem wyników testowania. Proszę zwrócić uwagę na to, 
czy podczas testów nie uwidoczniło się działanie JIT.


## Implementacja
W ramach zadania wykonano program, który aplikuje podany w pliku csv filtr do zdjęcia w formacie bmp. 
Następnie, jeśli zdjęcie było w rgb, zostaje ono zapisane jako przefiltrowane oraz w skali szarości. Utworzono
interfejs z metodą do obliczenia splotu oraz klasę z prywatną metodą natywną, która jest implementowana w c++.
Projekt budowany jest przy pomocy skryptu build.sh oraz dla testów test.sh, uruchomienie programu odbywa się poprzez
run.sh a następnie podanie zdjęcia do przetworzenia, oraz filtru który ma zostać zastosowany.

## Przeprowadzone testy
Przeprowadzono testy, w których przetwarzano dwa obrazy przy pomocy filtra 3x3, przy każdym uruchomieniu obserwowano
czas przetwarzania w zależności od metody obliczania macierzy (natywna, javowa). Następnie powtórzono pomiary dla
wyłączonego java JIT.

Testy wywoływano przy pomocy komend:

Test java z JIT 
```bash
java -Djava.library.path=. -cp bin pl.pwr.jk.jni.Main imgs/landscape.bmp kernels/sobelX.csv
```

Test java bez JIT
```bash
java -Djava.compiler=NONE -Djava.library.path=. -cp bin pl.pwr.jk.jni.Main imgs/landscape.bmp kernels/sobelX.csv
```

Test natywnie
```bash
java -Djava.library.path=. -cp bin pl.pwr.jk.jni.Main imgs/landscape.bmp kernels/sobelX.csv native
```

### Wyniki pomiarów

#### Dla obliczania splotu w javie z JIT
<table>
<tr>
    <th>Elapsed time</th>
    <th>File name</th>
    <th>Filter name</th>
    <th>Matrix height</th>
    <th>Matrix width</th>
    <th>Filter height</th>
    <th>Filter width</th>
</tr>
<tr>
    <td>229909us (0.229s)</td>
    <td>imgs/birb.bmp</td>
    <td>kernels/sobelX.csv</td>
    <td>1920</td>
    <td>1920</td>
    <td>3</td>
    <td>3</td>
</tr>
<tr>
    <td>36223us (0.036s)</td>
    <td>imgs/flower.bmp</td>
    <td>kernels/sobelX.csv</td>
    <td>427</td>
    <td>640</td>
    <td>3</td>
    <td>3</td>
</tr>
<tr>
    <td>30667us (0.030s)</td>
    <td>imgs/landscape.bmp</td>
    <td>kernels/sobelX.csv</td>
    <td>428</td>
    <td>574</td>
    <td>3</td>
    <td>3</td>
</tr>
</table>

#### Dla obliczania splotu w javie bez JIT
<table>
<tr>
    <th>Elapsed time</th>
    <th>File name</th>
    <th>Filter name</th>
    <th>Matrix height</th>
    <th>Matrix width</th>
    <th>Filter height</th>
    <th>Filter width</th>
</tr>
<tr>
    <td>6391330us (6.3s)</td>
    <td>imgs/birb.bmp</td>
    <td>kernels/sobelX.csv</td>
    <td>1920</td>
    <td>1920</td>
    <td>3</td>
    <td>3</td>
</tr>
<tr>
    <td>435234us (0.435s)</td>
    <td>imgs/flower.bmp</td>
    <td>kernels/sobelX.csv</td>
    <td>427</td>
    <td>640</td>
    <td>3</td>
    <td>3</td>
</tr>
<tr>
    <td>401843us (0.401s)</td>
    <td>imgs/landscape.bmp</td>
    <td>kernels/sobelX.csv</td>
    <td>428</td>
    <td>574</td>
    <td>3</td>
    <td>3</td>
</tr>
</table>

#### Dla obliczania splotu natywnie
<table>
<tr>
    <th>Elapsed time</th>
    <th>File name</th>
    <th>Filter name</th>
    <th>Matrix height</th>
    <th>Matrix width</th>
    <th>Filter height</th>
    <th>Filter width</th>
</tr>
<tr>
    <td>21314592us (21.3s)</td>
    <td>imgs/birb.bmp</td>
    <td>kernels/sobelX.csv</td>
    <td>1920</td>
    <td>1920</td>
    <td>3</td>
    <td>3</td>
</tr>
<tr>
    <td>1585954us (1.5s)</td>
    <td>imgs/flower.bmp</td>
    <td>kernels/sobelX.csv</td>
    <td>427</td>
    <td>640</td>
    <td>3</td>
    <td>3</td>
</tr>
<tr>
    <td>1376233us (1.4s)</td>
    <td>imgs/landscape.bmp</td>
    <td>kernels/sobelX.csv</td>
    <td>428</td>
    <td>574</td>
    <td>3</td>
    <td>3</td>
</tr>
</table>

## Porównanie wyników i wnioski
<table>
    <tr>
        <th></th>
        <th>Java z JIT</th>
        <th>Java bez JIT</th>
        <th>Natywnie</th>
    </tr>
    <tr>
        <th style="text-align: left;">imgs/birb.bmp</th>
        <td>0.23s</td>
        <td>6.3s</td>
        <td>21.3s</td>
    </tr>
    <tr>
        <th style="text-align: left;">imgs/flower.bmp</th>
        <td>0.036s</td>
        <td>0.435s</td>
        <td>1.5s</td>
    </tr>
    <tr>
        <th style="text-align: left;">imgs/landscape.bmp</th>
        <td>0.030s</td>
        <td>0.401s</td>
        <td>1.4s</td>
    </tr>
</table>

### Wnioski
Można zauważyć znaczną przewagę javy nad metodą natywną. Wyniki oznaczają że algorytmy optymalizujące w javie działają
dużo lepiej od natywnych, niezoptymalizowanych metod. Można zauważyć również znaczący wpływ JIT dla kodu w javie który
zdołał przyśpieszyć javowy algorytm 24 krotnie. Metoda natywna w porównaniu do javy z włączonym JIT wypada 100x wolniej.