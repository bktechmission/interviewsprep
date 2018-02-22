package Coding;


class Something {
    private Something() {}

    private static class LazyHolder {
            private static final Something INSTANCE = new Something();
    }

    public static Something getInstance() {
            return LazyHolder.INSTANCE;
    }
}

public class OnDemandHolderIdiom {
	public static void main(String[] args) {
		Something.getInstance();
	}
}
