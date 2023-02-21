
export function uniqueID() {
  let time = Date.now().toString();
  let array = Array.from(time);
  array = shuffle(array);
  let num = Number(array.join(''));
  let id = num + Math.ceil(Math.random() * 10000);
  this.form.id = id;
}

//打乱数组
export function shuffle(arr) {
  let temp, length = arr.length;
  for (let i = 0; i < length - 1; i++) {
    let index = Math.floor(Math.random() * (length--));
    temp = arr[index];
    arr[index] = arr[length];
    arr[length] = temp;
  }
  return arr;
}
