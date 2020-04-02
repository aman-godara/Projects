

let leap_year x = if x mod 400 = 0 then true else if (x mod 4 =0 && x mod 100 != 0 ) then true    else false ;;

