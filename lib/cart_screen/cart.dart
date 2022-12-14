import 'package:fbroadcast/fbroadcast.dart';
import 'package:flutter/material.dart';

class CartView extends StatefulWidget {
  const CartView({Key? key}) : super(key: key);

  @override
  State<CartView> createState() => _CartViewState();
}

class _CartViewState extends State<CartView> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Cart screen"),
      ),
      body: Container(
        width: double.maxFinite,
        color: Colors.red,
        child: Column(
          children: [
            MaterialButton(
              color: Colors.blue,
              onPressed: () {
                FBroadcast.instance(context).broadcast(
                  "cart",
                 );
              },
              child: const Text(
                "send data",
              ),
            ),
          ],
        ),
      ),
    );
  }
}
