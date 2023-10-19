var intervalSource = Rx.Observable.interval(1000).take(50);
var hot = intervalSource.publish();

var subscription1 = hot.subscribe(
    x => console.log('Observer 1: onNext: %s', x),
    e => console.log('Observer 1: onError: %s', e),
    () => console.log('Observer 1: onCompleted'));

setTimeout(() => {
    hot.connect();

    setTimeout(() => {
        var subscription2 = hot.subscribe(
            x => console.log('Observer 2: onNext: %s', x),
            e => console.log('Observer 2: onError: %s', e),
            () => console.log('Observer 2: onCompleted'));
    }, 5000);
}, 5000);

