package bodata;

public class BOPopularity implements Comparable <BOPopularity>
{
	int searchCount;
	int bookingCount;
	
	public BOPopularity()
	{
		
	}
	
	public int getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}

	public int getBookingCount() {
		return bookingCount;
	}

	public void setBookingCount(int bookingCount) {
		this.bookingCount = bookingCount;
	}

	@Override
	public String toString() {
		return "BOPopularity [searchCount=" + searchCount + ", bookingCount="
				+ bookingCount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bookingCount;
		result = prime * result + searchCount;
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
		
		BOPopularity other = (BOPopularity) obj;
		if (bookingCount != other.bookingCount)
			return false;
		if (searchCount != other.searchCount)
			return false;
		return true;
	}
	
	@Override
	public int compareTo(BOPopularity boPopularity)
	{
		return Integer.compare(searchCount, boPopularity.getSearchCount());
	}
	
}
