public class DogTeam {

	private LLDogNode head;

	public DogTeam(Dog dog) {
		head = new LLDogNode(dog, null);
	}

	public void printTeam() {
		LLDogNode cur = head;
		int dogNumber = 1;

		System.out.println("----------------");
		while (cur != null) {
			System.out.println(dogNumber + ". " + cur.getContents().getName() + ", " + cur.getContents().getWeight());
			cur = cur.getLink();
			dogNumber += 1;
		}
	}

	public static void main(String[] args) {

		DogTeam team = new DogTeam(new Dog("dog1", 60));
		team.printTeam();
		System.out.println("weightDiff: " + team.weightDiff());

		team.insertTail(new Dog("dog0", 5));
		team.insertHead(new Dog("dog2", 90));
		team.printTeam();
		System.out.println("weightDiff: " + team.weightDiff());

		team.insertHead(new Dog("dog3", 7));
		team.insertAfter(new Dog("dog4", 100), "dog2");
		team.printTeam();

		team.insertTail(new Dog("dog10", 205));
		team.insertAfter(new Dog("dog9", 75), "dog10");

		team.printTeam();
		System.out.println("weightDiff: " + team.weightDiff());

	}

	public void insertHead(Dog dog) {
		LLDogNode node = new LLDogNode(dog, head);
		head = node;
	}

	public void insertTail(Dog dog) {
		LLDogNode node = head;
		while (node.getLink() != null)
			node = node.getLink();
		node.setLink(new LLDogNode(dog, null));
	
	}

	public double weightDiff() {
		double max = head.getContents().getWeight();
		double min = head.getContents().getWeight();
		
		LLDogNode node = head;
		while (node != null)
		{
			if (node.getContents().getWeight() > max)
				max = node.getContents().getWeight();
			if (node.getContents().getWeight() < min)
				min = node.getContents().getWeight();
			node = node.getLink();
		}
		return max - min;
	}

	public void insertAfter(Dog dog, String dogName) {
		LLDogNode node = head;
		while (!node.getContents().getName().equals((dogName)))
		{
			node = node.getLink();
		}
		node.setLink(new LLDogNode(dog, node.getLink()));
	}
}
