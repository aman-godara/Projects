let middleChild x y z = if x=y && y=z then -2 else if x=y || y=z || z=x then  -1 
else  if (x>0 && y>0 && z>0) then x+y+z - max  (x) (max y z) - min  (x) (min y z  )
else -3 ;; 


 




let print_middle_child x y z = if (middleChild x y z = -1 ) then "There are twins!" else if (middleChild x y z = -2 ) then "There are triplets" else if (middleChild x y z > 0) then "The age of the middle child is: " ^ string_of_int(middleChild x y z ) else "Invalid inputs!" ;;  

