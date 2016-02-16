select w.id, w.word, GROUP_CONCAT(u.url) as urls 
from words w, urls u, words_in_url wiu
where w.id = wiu.word_id && u.id = wiu.url_id
group by w.id;
