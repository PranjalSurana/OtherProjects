function calc_pi() {
    let n = 200000;
    let pi = 0;
    for (var i = 0; i < n; i++) {
      let temp = 4 / (i * 2 + 1);
      if (i % 2 == 0) {
        pi += temp;
      }
      else {
        pi -= temp;
      }
    }
    return pi;
  }

  let pi = calc_pi();
  
  console.log(pi);
  