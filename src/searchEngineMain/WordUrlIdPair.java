//Project by Gregory Bretall (21284961), Miles Bonner (82127215), Zach Anderson (22109634), and Lauren Dimailig (73117811)
package searchEngineMain;

public class WordUrlIdPair 
{
	private String word;
	private String url;
	
	WordUrlIdPair(String word, String url)
	{
		this.word = word;
		this.url = url;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public String getWord()
	{
		return word;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordUrlIdPair other = (WordUrlIdPair) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
	
	
}
