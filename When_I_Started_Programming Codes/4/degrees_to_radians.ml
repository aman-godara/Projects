let check_degrees_to_radians x = if (x>=0.0 && x<= 360.0) then x else if (x>360.0) then mod_float x 360.0 else 360.0 -. mod_float (-1.0*. x) 360.0 ;;


let pi = 4.0 *. atan(1.0) ;;

 let degrees_to_radians x = let trans  = check_degrees_to_radians x in (trans*. pi /. 180.0 ) ;;


