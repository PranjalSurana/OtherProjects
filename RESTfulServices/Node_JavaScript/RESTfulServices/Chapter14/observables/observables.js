var intervalSource = Rx.Observable
    .interval(1000)
    .take(50);

var hotSource = Rx.Observable
    .interval(1000)
    .take(50);
var hot = hotSource.publish();
hot.connect();

function registerColdObserver1() {
    $("#coldSubscription1").html('Starting Cold Observer 1');
    var observer1 = Rx.Observer.create(
        x => $("#coldSubscription1").html('Next Value: ' + x),
        e => console.log('onError: %s', e),
        () => console.log('onCompleted'));

    intervalSource.subscribe(observer1);
}

function registerColdObserver2() {
    $("#coldSubscription2").html('Starting Cold Observer 2');

    // TODO: complete the definition of the second cold observer (copy the first one)
}

function registerHotObserver1() {
    $("#hotSubscription1").html('Starting Hot Observer 1');
    hot.subscribe(
        x => $("#hotSubscription1").html('Next Value: ' + x),
        e => console.log('onError: %s', e),
        () => console.log('onCompleted'));
}

function registerHotObserver2() {
    $("#hotSubscription2").html('Starting Hot Observer 2');

    // TODO: Complete the definition of the second hot observer (copy the first one)
}

