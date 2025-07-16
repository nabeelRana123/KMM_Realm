import SwiftUI
import shared

struct ContentView: View {
    
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        // Access the Kotlin function using the correct Swift naming convention
        return MainKt.getComposeViewController( nativeViewFactory: IOSNativeViewFactory.shared)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
