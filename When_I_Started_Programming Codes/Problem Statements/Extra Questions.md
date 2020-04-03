### Practice Question(s)

Consider the following function which calculates an. 

let rec power a n =
	if n = 0
	then 1
	else
		let half_pow = power a (n/2) in
		if n mod 2 == 0
		then half_pow * half_pow
		else a * half_pow * half_pow;;
Prove by induction that the power function correctly computes an ∀n≥0.
Assuming every basic operations (∗,mod,==,etc.) takes unit time to execute prove that if T(n) is the running time of (power a n) then T(n)=Θ(log(n)).
 

P.S. : I'll add more questions / brain-teasers if I get enough time to think some. Best of luck to all!


### Practice questions

 (Easy one) Write a recursive function to calculate an.
power a n : int -> int -> int

Given any 2 positive no. a and b prove the following:
                     gcd(a,b)=gcd(a,b−a)  if  b>a
(Here, gcd means the greatest common divisor)
Now write a recursive function to calculate the gcd of two no. (Think about what will be the base case(s)).
gcd a b : int -> int -> int
Consider a game of two players. There are n gulab-jamuns on the table. In each turn a player can eat either 1 or ⌈n/2⌉ gulab-jamuns from the table. Players alternate their turns. The player who is left with no gulab-jamuns to eat on the table in his turn loses. For example:
Consider n=9.
Here 1st player can eat either 1 or 5 gulab-jamuns. Suppose he ate 5 gulab-jamuns. Remaining no. of gulab-jamuns: 4.
Then 2nd player can eat either 1 or 2 gulab-jamuns. Suppose she ate 2. Remaining no. of gulab-jamuns: 2.
Then 1st player can only eat 1 gulab-jamun. And then 2nd player can eat the last gulab-jamun.
Here, 1st player loses since he is left with no gulab-jamun to eat and 2nd player wins.

Given the initial no. of gulab-jamuns n on the table write a function which returns true if 1st player can win if both players play optimally. By optimally I mean both players are rational & intelligent and they do not make any wrong move if they can win the game.
can_I_win n : int -> bool

(Moral of this question: When you've figured out the logic you'll learn an important lesson in life. It's beneficial sometimes to control your hunger/greed in order to taste success.)


### Practice Questions

Sorting is a technique in which you are given a sequence of numbers and your aim is to produce another sequence of those numbers such that the sequence is in non-decreasing order. Consider the following code for sorting an Array of numbers:

# let swap a i j =
	let temp = a.(i) in
	a.(i) <- a.(j);
	a.(j) <- temp;;
val swap : 'a array -> int -> int -> unit = <fun>

# let sortArray a =
	for i=0 to Array.length a - 1 do
		for j=1 to (Array.length a - 1) - i do
			if a.(j-1) > a.(j)
			then swap a (j-1) j
			else ();
		done;
	done;;
val sortArray : 'a array -> unit = <fun>(i) Briefly describe what swap function is doing.
(ii) sortArray is the main function which actually sorts the numbers given in array a. Figure out a property of the array a which holds when the outer for loop has executed i times.
(iii) Using this property try to prove the correctness of the function sortArray i.e., you need to prove that after the execution of outer for loop n times the resulting array is in sorted order. (n is the length of the array a)
(iv) Figure out the time complexity of the function sortArray.

(i) Consider a polynomial p(n) of degree d as follows:
p(n)=a0+a1∗n+a2∗n2+...+ad−1∗nd−1+ad∗nd where ad is +ve.
Prove that p(n)=Θ(nd).

(ii) Prove that n∗log(n)=Θ(log(n!)).
This might be difficult. Don't be disheartened if you cannot prove this! It's a hard question.



### More practice questions
 

Suppose you have an empty Stack and a Queue of numbers. Your task is to put the numbers in the Stack such that the numbers are arranged in non-decreasing order if seen from top to bottom of the Stack. Figure out a procedure for doing this. Try to write code for this procedure in OCaml. Note that you can NOT use any other data structure in this procedure. What is the time complexity of your solution?
Suppose you have a stack of plates arranged in a random fashion. All the plate sizes are different from each other. You can observe the whole stack of plates.
What you need to do is to sort the plates in increasing order from top to bottom. But the only allowed operation is to pick some top k plates, reverse their order and put them back on the stack. Try to come up with an algorithm for this task such that the (worst case) no. of these kind of operations are minimized.


Consider that you have n numbers. If you want to find the minimum element among these you must compare atleast n−1 pairs of numbers (think why?). For e.g. if we have the list of numbers [a1; a2; a3] then we must make 2 comparisons to find out the minimum element (compare a1 with a2 and then compare the minimum among them with a3). Similarly for finding the maximum element among these you must compare atleast n−1 pairs of numbers too!

But what is interesting is the case when you want both the maximum as well as minimum element simultaneously. In this case you can surely get these numbers in 2∗(n−1) comparisons. But can you get both of these numbers in lesser comparisons?
For e.g. if you have 4 numbers: a1,a2,a3,a4 then you can compare a1 with a2 & a3 with a4. Then you can compare min(a1,a2) with min(a3,a4) to get the minimum number. And similarly you can compare max(a1,a2) with max(a3,a4) to get the maximum number. So, overall you need 4 comparisons to get both the minimum & maximum element.

Try to come up with a way to achieve this task with lower no. of comparisons than 2∗(n−1)...
