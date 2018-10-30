<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@include file="jspf/cabecalho.jspf" %>
    <div class="container text-center">
        <h1> Meu Restaurante </h1>
    </div>
    
        <div class="container text-center">

            <div class="row" style="margin-top: 1.5cm">
        <div class="col-sm-4">
            <div class="card">
                <div class="card-body">
                    <a href="FrontController?action=AdministrarFuncionario&id=${idRest}" >
                    <h5 class="card-title">Funcionários</h5>
                   <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAIxSURBVGhD7dm9axRBHMbxE99QERFFbAL+AYqVL4UgdiKoRVBEG3tFsBAL/wKx8RW0UtRIiCBWIURsooK1YCW+F0KihIj4mhe/j97CMPu7vV0zdzuH88CHy83u7O3vbnZ3dtNI+Q+yDicxhnHM4Qde4g72YCGizQpcwU9o54u8x35Ely14BWuni9zEMtSSRVjw988/2YrPsHa0jFEsQZbFzdfg2YDTeIwPmMU0NDzuNV+tHaziAZ5gqvn+G15Dx9RBuIVWzlqUHfOd9haHUTmb8QbWRut0A0tRKpswnzHfacNoe/pegxh/Cd85FOYCrI6xmcFGmOmDrsBWxxjdhxlNK6wOsdLZdBVyeQirQ8x0jclF52pr5ZidQS69dHxkLiIXTQuslWN2Hrn0wvXDpzlgLndhrRyzXcjlKKyVYzUBc961HB9hdYrRWbTMMVidYqMvfDVaRrPKEVidY6Ebu71oG132n8LaSN1UxHGUjp6I3IK1sbp8Qj/+Kbuhe3Vrwy5dgya9toyerrS6UXuBL16b7ysuYz3mFQ016wMy+qb0cGCn05Z5Bx13+5y2zHPoacwhp80ygCBpV4gmm4pukf1lz6DsgL/sERRrmSsV4qfThVhD0tW1Qn5B05tLTlumqBCdAA5g0GmzdK2QIt9xBNedtqqiKCSEVIifVEggwQrRfcpQjU4gxY3+g6TzfV22IUjSwR5IKsRPKiSQVIiflbA+oFtuI1hO4VoNrmI7UnosjcZvjKq8ornk5lIAAAAASUVORK5CYII=">
                    </a>
                </div>
            </div>
        </div>
    <div class="col-sm-4" >
            <div class="card">
                <div class="card-body">
                <a href="FrontController?action=Cardapio">
                    <h5 class="card-title">Cardápio</h5>
                   <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAQySURBVGhD7ZlryE1ZGMdfQwbjGikl11ySa8olRFFynQ/CjFJuKeGTD3IrKZeEqEnRuJWEFB+IaZovRJGQS0KSW0m5NWFcx/+37ee033PW2Xud95z3fffR+devd69nrb32+5y9nrWetXZVRRVV5K3fxSXxUfxfJP+KU2KoQI3EUnFLfBKuewrhpTgouotq2iGs0RdBw5ryWlhf/NNzxV8RGz+U6z5f+JGsrzdiuAg0U2CkwXzRQhSr9mK9+CrsoQ/FRNFYFKtu4qig3yeimag6FxoWUiix9glzZAiGEqqhuCzoezaGd2GhLYUENRBNv196abyg7wdBqfRaIeh/GwUuIE4E62rxSnwWV8UkkaR+gr5567WhRYL+d1JIcoS3wAxh7ewNMv4J5DilypEFgvoXYrD4SSwROPKf6CnyKZ8jzUWbBFqLqFqKVt8vM/J2hCH1VFA/A0NE2wX2I0HJLZcjIwXD054bx0aBeLbZ1mAI5e3IVEHd3aBUXe0Ew4y1ogMGh1yO9BE3xf0E7gmWA8RacUdgm4UhlLcjuwR1G4JSrmwuZ/i5lJoYuS6oy6yeWSJWqP8zKOUqNY4QzNSxUrvESk392aCUq3yOMBRZnePoLJhYTB0FNmZRk5cjLHzY3wclt/oK2twISrlyOTJaRFOXOLYIRFyYbR2GUF6O8Gswu/BW8skcIWt2yeVID3FFuAI8CsFtgU0WzTC/LaZhCOU9tB4J6joFpVz9Kqg/FpRylZoY2S2oI83PFmP1tKA+X8KZGkd6iw+CesamBRqpuGW2mTTaoXyOMLzIEuLoL8hwTaw/9Be1eTuCGKe2Ei/DIO0XlNlE2S7QJZcjY4U9MwmyBzRHmI19jqkgRxABRhsC7hfBav5W9BJxcjnSVTBds5eI46KYLtAgcUFgm4IhVMGOkKzRhl0egW/XSUpNjJgqjgjaZjtC4LoCPArD6WeBmGQI/oEiuuevV0ds++vDHwLNE2bbjCFUvTrC/RwPuQI8CsHNNgINEP+I84IfwlSJkR/GkceiFI4ME+MSGCMsYyCBHSXInG0CQDV2hJXcHCFDTZLLkQnCnplE8A9K5HJms9QeFewIvwJt2JtY6s4qnySXI2yqToi/EzgjLLDJ+TgUJ0nlTZkKdgTZeZbtCn3GfepiBLF5ot3J8C8HE0lKpSMrhbWF1B6ZRjf1LrEPWC4Oi+D020N16sizsBB39FlT1bYjWwX9BwcSB8LCIRE9fimFatORLsK+jo3AwLaTT1gY2PAsFmxoioGFq4nIdoQ1aLJw3ePLb4I3wGc4+j4uMmJufi6oKBX0tym85txrr/A9z/KFGZRT+mpi9SZ49gjOdIuBqZqPqtkP5tMEC53rHl+YbPhCRRpTJ+ItR7/Akrrz3aMstUrgBIcV+c6Py0IcFeHItaBUxmIRXSvIzyqqqPxUVfUNHE+GHUyeZtcAAAAASUVORK5CYII=">
                </a>
                </div>
           </div>
        </div>
     <div class="col-sm-4" >
            <div class="card">
                <div class="card-body">
                 <a href="FrontController?action=Pedidos">
                    <h5 class="card-title">Pedidos</h5>
                  <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAUlSURBVGhD7Zl5qG1THMeveciUkGQoQx4SkTGUIUQK8Z6XIUPG8AciQoaEMmdImTKVsRdepoR4JEPmIUNkHjPP0+ez3d+1zz5rrbPPvefVkfOtT93127+1zv7tvdf6/da6YyONNNJII4000v9Ui8JC//z539IacAo8Bd/CX+N8DA/DEbAMDK2WhSvhN4ibl2/gh4btRzgRFoSh0nrwHniTv8AVsC0sAouDN7wU7A63w5+g77OwHAyF1oWvwRt7CFaGkAF8Co9WrX+1IbwO9nkVHGPVHszVebY0vA3e0HUwP9S1JHjNt9WUb+hx8HobfoWLYT4YuC4Ef8RJnPreS4Eo59U7oM934EPJEXPvUBioVoCfwR9YU0NCvQJRzpvwab7RuvYB/e6qWgPU0eDAN1attNoEMg+8CPrtoCGjLUCfx6rWAPUAOPBuVSutNoGo00G/C6pWWvVA/BpSC0Kd1ouD360Dr1i10loF9PkCSjljZ9DPh5NTBNKW3+E2WAmK+gnssEDVSusSiIEP1pCRy7E+5pWc6oF8Bs8UeAFc5fT9BFzes3KV0dGEl9JmEPnFBLgN5LQ56PdI1UorAvkIltDQQ+azOWCf92F5SOoN0GntqtWpehC3wCZQ0kzQ9+aqlVavyT4vWOf5doMt4S2wn8k6mYOuBx1cvepykjknvGaSbJPArgb9j6laaZUC2RTeBa+XOA26tCd48WlwCQ35twGcB22C8DP5EhxrNQ0Z5QJZGD4Er30AqTkjTn6xBuyQq1AMMF3DJHUWOMb9VSuvXCC+De1O8FJCPQn0czvRNV/2BS/6Ka2uoU/5dOJJWUGXlAsklu57qlZefin3gb5d88WLXhBrpnWgrczi7lXse4KGHppqIMraLr4iN3gd0hi4IzwW3Ifk5O7wfPAt2OcqqM+xnAYRiNoL9P8cOrK/RnOKu0P/DidXooPAH9oFfAJm2u9BH4vN46CtcoHsCNr9bNrIZTpWOAvWCWkwZ6jtwE2Uthx/wB0wDfpRLhBLJO1WGvuD99CL2WAfa7wJaYhAQt6k+cW3cjfcCZYrvqHJbm1zgajLwGv9cjlMSEMzkLmhUiDOsQPA84AHW/AaDGUg/epwGAUyVQ1FIJbUlvFbgScvk1GbQFxkXJUsW0q5rO9ALOefAH0CE6HLr7vHflQKxCQX5XpgvvKUJ7Vf6iuQGRBHOFa394IbJ49LtX0F60Nb5QI5F7SLic4c4blzVA4vgaVJXa0D8RXHVtikU9+vW6KY4b3mvr/0CdSVCmQP0ObZ8t5QL3XcZD0HXm8eISUDMVM7UH3vfg3oaKJKycrTp6ZPV/GWkbs9/e0Xehm0WYWn5JuwXNJnYw3jiqOsS6vWuOIM9wZwsyUeDmgrzQP3MPo8CdGvxCzQ3wd3Mlgl2HYPXyo6zwH9rgXHMXF6PKXtSJiQhVd8j3UsJEtyr9/s0wtP+ps2j2tL2g/081S03s/D88WgQxuBpfmt49jJk5Mux5q2Bgf0iUa/EmeDB2/bg/+2sGy3/ytQknsd/fwkHecmcKuRO/3pUJxClg6cYx6dWbX6l/MsDjk20JCQPs+DPrtq6Fc7gZ1dzTyzauoQ8I25SPQ8CSzoVPB33oTmfDQIJ3NcLx0kFuXrdxBzia/0KHAj5Tet3UAOhKnIZX0OOJ5z0uXUVdAAnQPafVi9ztWKcifmgJFP6riqmTAHIb91z9ji33l1nD/O34HIo5fD4CJwQTBxlRaByWotOB78nM4At8Cl4yE0NvY3GTkS4DVpcy0AAAAASUVORK5CYII=">
                </a>
                </div>
           </div>
        </div>
    </div>
    </div>
<%@include file="jspf/rodape.jspf" %>