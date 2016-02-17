SELECT * 
FROM wordrelations.words
order by id;

SELECT * FROM wordrelations.urls order by id;

select w.id as word_id, w.word, GROUP_CONCAT(u.url) as urls 
from words w, urls u, words_in_url wiu
where w.id = wiu.word_id && u.id = wiu.url_id
group by w.id;


select u.id as url_id, u.url as url, GROUP_CONCAT(w.word) as words 
from words w, urls u, words_in_url wiu
where w.id = wiu.word_id && u.id = wiu.url_id
group by u.id;