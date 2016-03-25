package next.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.collect.Lists;

import next.CannotDeleteException;

@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long questionId;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;
	
	@Column(length = 50, nullable = false)
	private String title;
	
	@Column(length = 5000, nullable = false)
	private String contents;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createdDate;
	
	private int countOfComment;
	
	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    @OrderBy("answerId ASC")
	private List<Answer> answers;
	
	public Question() {
	}
	
	public Question(User writer, String title, String contents, List<Answer> answers) {
		this(0L, writer, title, contents, new Date(), 0, answers);
	}	
	
	public Question(Long questionId, User writer, String title, String contents,
			Date createdDate, int countOfComment, List<Answer> answers) {
		this.questionId = questionId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
		this.countOfComment = countOfComment;
		this.answers = answers;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getCountOfComment() {
		return countOfComment;
	}

	public void setCountOfComment(int countOfComment) {
		this.countOfComment = countOfComment;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public boolean isSameWriter(User user) {
		return user.isSameUser(this.writer);
	}
	
	public Question writeBy(User user) {
		return new Question(user, title, contents, Lists.newArrayList());
	}
	
	public void update(Question newQuestion) {
		this.title = newQuestion.title;
		this.contents = newQuestion.contents;
	}
	
	public void addAnswer() {
		this.countOfComment += 1;
	}
	
	public boolean canDelete(User loginUser) throws CannotDeleteException {
		if (!loginUser.isSameUser(this.writer)) {
			throw new CannotDeleteException("다른 사용자가 쓴 글을 삭제할 수 없습니다.");
		}
		
		for (Answer answer : this.answers) {
			if (!answer.canDelete(loginUser)) {
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
