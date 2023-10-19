var intervalSource = Rx.Observable.interval(1000).take(500);

var subscription1 = intervalSource.subscribe(
    x => console.log('Observer 1: onNext: %s', x),
    e => console.log('Observer 1: onError: %s', e),
    () => console.log('Observer 1: onCompleted'));

var subscription2 = intervalSource.subscribe(
    x => console.log('Observer 2: onNext: %s', x),
    e => console.log('Observer 2: onError: %s', e),
    () => console.log('Observer 2: onCompleted'));

setTimeout(() => {
    subscription1.dispose();
    subscription2.dispose();
}, 5000);