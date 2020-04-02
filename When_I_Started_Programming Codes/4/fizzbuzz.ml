 let fizzbuzz x = if ( x mod 3 =0 && x mod 5 =0) then "Fizzbuzz" else if ( x mod 3 = 0 ) then 
"Fizz" else if ( x mod 5 = 0 ) then "Buzz" else string_of_int (x) ;;



 

let rec fizzbuzz_string x = if x>1 then fizzbuzz_string (x-1) ^" "^ fizzbuzz (x) else if x=1 then fizzbuzz (x)  else "-1";; 
