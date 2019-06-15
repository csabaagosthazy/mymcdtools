var aphorisms = [{aphorism :"Just because something doesn’t do what you planned it to do                       doesn’t mean it’s useless.", from :"Thomas Edison"}, 
                {aphorism:"It has become appallingly obvious that our technology has exceeded our humanity.", from:"Albert Einstein"}, 
                {aphorism:"We are stuck with technology when what we really want is just stuff that works.", from:"Douglas Adams"},
                {aphorism:"It’s supposed to be automatic, but actually you have to push this button.", from:"John Brunner"},
                {aphorism:"The human spirit must prevail over technology.", from:"Albert Einstein"},
                {aphorism:"Technology… the knack of so arranging the world that we don’t have to experience it.", from:"Max Frisch"},
                {aphorism:"Communications tools don’t get socially interesting until they get technologically boring.", from:"Clay Shirky"},
                {aphorism:"Programs must be written for people to read, and only incidentally for machines to execute.", from:"Harold Abelson"},
                {aphorism:"Technology is the campfire around which we tell our stories.", from:"Laurie Anderson"},
                {aphorism:"Once a new technology rolls over you, if you’re not part of the steamroller, you’re part of the road.", from:"Stewart Brand"}];

var randIndex = Math.floor((Math.random()*aphorisms.length));
var outputap = aphorisms[randIndex].aphorism
var output_from = '-' + aphorisms[randIndex].from;

document.getElementById("divOutputap").innerHTML = outputap;
document.getElementById("divOutputfrom").innerHTML = output_from;
