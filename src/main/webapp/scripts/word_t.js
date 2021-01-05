const FONT = "50px arial";
const TEXT_COLOR = "#333";

var activeWords = [];

word_t = function(word, color, x, y, width, height, vx, vy) {
  this.word = word;
  this.color = color;

  this.x = x;
  this.y = y;

  this.width = width;
  this.height = height;

  this.vx = vx;
  this.vy = vy;
}