compulsory + am inceput sa lucrez la homework(mai am de implementat butonul de load si save)

Variant 1: Each player tries to create a triangle made solely of his color. The player who succeeds, wins the game. 
. am utilizat SWING
. am implementat mouse listener
. am implementat algoritm AI ce cauta in graf triunghi 2/3 color si coloreaza muchia necolorata ( functioneaza pentru cazul triunghiului colorat de adversar pentru ca il impiedica sa câștige jocul, si pentru cazul cind triunghiul este colorat 2/3 de catre noi pentru ca câștigăm jocul) in cazul in care nu exista astfel de triunghiuri, coloram prima muchie necolorata 
. am implementat clasa AbstractEdge be baza careia se contruieste logica jocului(pastram informatii despre index,nod sting,nod drept, colorat sau nu si alte date)
. am implementat algoritmulJGraphT pentru a determina numarul de triunghiuri