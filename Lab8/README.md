compulsory + homework + bonus + bonus x2

Am creat tabelele :
artists(id,name)
genres(id,name)
albums(id,year,title,artist_id)  
genre_album(id_album,id_genre)
playlists(id,name,time_created)
tabela pentru 2 playlist-uri implementate prin algoritmi diferiti(clustering si clique) (id,album_id)
tabela pentru fiecare album (id,file_name,file_data)  datele in fisier sunt stocate binar
(total 24 tabele)

Am utilizat un connection pool HikariCP
Am importat un data set propus in laborator
Am implementat algoritmul DegeneracyBronKerboschCliqueFinder JGraphT
Am implementat algoritmul ClusteringCoefficient

Am importat un data set ce contine fisiere .mid
Am populat tabelele pentru albume cu fisierele audio
Am creat o interfata in Swing (Player) ce permite interpretarea fisierelor audio pe baza datelor binare stocate
