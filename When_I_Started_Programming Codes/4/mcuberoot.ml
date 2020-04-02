let check_mcuberoot x y z = if ( x*.y*.z > 0.0) then true else false ;;

 let mcuberoot x y z = if (check_mcuberoot x y z = true) then (x*.y*.z)** (1.0/.3.0) else -1.0 ;;

