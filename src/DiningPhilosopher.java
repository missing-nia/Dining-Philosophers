import java.util.Random;

public class DiningPhilosopher 
{
	public static void main( String args[] )
	{
		Fork forklist[] = new Fork[5];
		
		for ( int i = 0; i < 5; i++ )
		{
			forklist[i] = new Fork();
		}
		
		for ( int i = 0; i < 5; i++ )
		{
			if ( i != 4 )
			{
				Thread t = new Thread( new Philosopher( forklist[i], forklist[i + 1], i + 1 ) );
				t.start();
			}
			else
			{
				// Fork to the right of the last philosopher should be the first fork
				Thread t = new Thread( new Philosopher( forklist[i], forklist[0], i + 1 ) );
				t.start();
			}
		}
		
	}
}

class Philosopher implements Runnable
{
	private Fork left;
	private Fork right;
	private String name;
	
	public Philosopher( Fork left, Fork right, int number )
	{
		this.left = left;
		this.right = right;
		this.name = "Philosopher " + number;
	}
	
	public void run()
	{
		Random rand = new Random();
		int seconds;
		
		while( true )
		{
			// Philosopher will always grab the left fork first to ensure deadlocks never occur
			System.out.println( name + ": attempt to acquire fork to left" );
			synchronized( left )
			{
				System.out.println( name + ": acquired left fork" );
				System.out.println( name + ": attempt to acquire fork to right" );
				synchronized( right )
				{
					System.out.println( name + ": acquired right fork" );
					right.Eat( name );
				}
			}
			
			try
			{
				// Sleep for x seconds
				seconds = rand.nextInt( 4 ) + 1;
				System.out.println( name + ": Thinking for " + seconds + " seconds" );
				Thread.sleep( 1000 * seconds );
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}

class Fork
{
	public void Eat( String name )
	{
		Random rand = new Random();
		int seconds = rand.nextInt( 9 ) + 1;
		
		try 
		{
			// Sleep for x seconds
			System.out.println( name + ": Eating for " + seconds + " seconds" );
			Thread.sleep( 1000 * seconds );
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
}