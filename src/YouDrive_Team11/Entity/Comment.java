package YouDrive_Team11.Entity;

/**
 * Represents a comment on a vehicle in the YouDrive system.
 * @author David Stapleton
 *
 */
public class Comment {

	/**
	 * The unique identifier for this comment in the YouDrive system
	 */
	private int id;
	
	/**
	 * The text of the comment
	 */
	private String content;
	
	/**
	 * Creates a new comment object from the parameters
	 * @param id		Unique identifier
	 * @param content	The text of the comment
	 */
	public Comment(int id, String content){
		this.id = id;
		this.content = content;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
}
