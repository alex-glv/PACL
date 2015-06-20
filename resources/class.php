namespace AThing;

class AbstractClass extends OtherClass {
    var $stupid_var;
    public $var;
    private $var2 = "literal";
    public function testfun() {
        if ($var == "test") {
            echo "hi!";
        }
    }
    private function testFun2($arg, $arg2) {
        return 'val';
    }
}

function grossStuff() {
} 