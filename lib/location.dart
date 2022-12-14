import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class LocationView extends StatefulWidget {
  LocationView({Key? key}) : super(key: key);

  @override
  State<LocationView> createState() => _LocationViewState();
}

class _LocationViewState extends State<LocationView> {
   var platform = const MethodChannel('example_service');

  String _serverState = 'Did not make the call yet';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        width: double.maxFinite,
        child: Column(

          children: [
          const SizedBox(height: 20,width: 20,),
            Text(_serverState),

            TextButton(
              onPressed: () async {
                try {
                  final result =
                      await platform.invokeMethod('startExampleService');
                  setState(() {
                    _serverState = result;
                  });
                } on PlatformException catch (e) {
                  print("Failed to invoke method: '${e.message}'.");
                }
              },
              child: Text("start service"),
            ),
            const SizedBox(
              height: 20,
              width: 20,
            ),
            TextButton(
              onPressed: () async {
                try {
                  final result =
                      await platform.invokeMethod('stopExampleService');
                  setState(() {
                    _serverState = result;
                  });
                } on PlatformException catch (e) {
                  print("Failed to invoke method: '${e.message}'.");
                }
              },
              child: Text("stop service"),
            ),
           ],
        ),
      ),
    );
  }
}
