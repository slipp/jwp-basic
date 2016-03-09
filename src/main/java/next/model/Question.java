package next.model;

import java.util.Date;
import java.util.List;

import next.CannotDeleteException;

public class Question {
	private long questionId;
	
	private String writer;
	
	private String title;
	
	private String contents;
	
	private Date createdDate;
	
	private int countOfComment;
	
	public Question(String writer, String title, String contents) {
		this(0, writer, title, contents, new Date(), 0);
	}	
	
	public Question(long questionId, String writer, String title, String contents,
			Date createdDate, int countOfComment) {
		this.questionId = questionId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
		this.countOfComment = countOfComment;
	}

	public long getQuestionId() {
		return questionId;
	}
	
	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	
	public long getTimeFromCreateDate() {
		return this.createdDate.getTime();
	}

	public int getCountOfComment() {
		return countOfComment;
	}
	
	public boolean isSameWriter(User user) {
		return user.isSameUser(this.writer);
	}
	
	public void update(Question newQuestion) {
		this.title = newQuestion.title;
		this.contents = newQuestion.contents;
	}
	
	public boolean canDelete(User user, List<Answer> answers) throws CannotDeleteException {
		if (!user.isSameUser(this.writer)) {
			throw new CannotDeleteException("다른 사용자가 쓴 글을 삭제할 수 없습니다.");
		}
		
		for (Answer answer : answers) {
			if (!answer.canDelete(user)) {
				throw new CannotDeleteException("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
			}
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", writer=" + writer
				+ ", title=" + title + ", contents=" + contents
				+ ", createdDate=" + createdDate + ", countOfComment="
				+ countOfComment + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (questionId ^ (questionId >>> 32));
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
		Question other = (Question) obj;
		if (questionId != other.questionId)
			return false;
		return true;
	}
}
