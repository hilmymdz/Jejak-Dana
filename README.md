# Jejak Dana - Aplikasi Pelacak Keuangan Pribadi

**Jejak Dana** adalah sebuah aplikasi Android modern yang dirancang untuk membantu pengguna melacak dan mengelola keuangan pribadi mereka dengan mudah dan efisien. Aplikasi ini dibangun menggunakan Kotlin dan komponen Android Jetpack modern, dengan fokus pada antarmuka yang bersih dan pengalaman pengguna yang intuitif.

## Tampilan Aplikasi

| Halaman Utama | 
| :---: |
| <img width="290" height="642" alt="Picture1" src="https://github.com/user-attachments/assets/0295a7a8-836a-49a9-9f90-f357fd594e07" />) | 


## Fitur Utama
- **Pencatatan Transaksi:** Catat pemasukan dan pengeluaran dengan cepat melalui formulir yang mudah diisi.
- **Ringkasan Keuangan:** Lihat ringkasan total pemasukan, pengeluaran, dan saldo akhir secara langsung di halaman utama.
- **Manajemen Anggaran:** Buat anggaran kustom untuk berbagai kategori pengeluaran (misalnya, Makanan, Transportasi) dengan periode mingguan, bulanan, atau tahunan.
- **Progress Bar Dinamis:** Pantau penggunaan anggaran Anda secara visual melalui progress bar yang diperbarui secara *real-time* setiap kali ada pengeluaran baru.
- **Riwayat Transaksi:** Lihat daftar semua transaksi yang telah Anda catat, lengkap dengan ikon kategori untuk identifikasi cepat.
- **Pengaturan Data:** Kelola data Anda dengan opsi untuk menghapus semua transaksi atau semua anggaran untuk memulai dari awal.

## Teknologi dan Arsitektur
Aplikasi ini dibangun dengan mengikuti praktik terbaik pengembangan Android modern.
- **Bahasa:** [Kotlin](https://kotlinlang.org/)
- **Arsitektur:** MVVM (Model-View-ViewModel)
- **Komponen Inti Jetpack:**
    - **UI Layer:** [Fragments](https://developer.android.com/guide/fragments) & [XML Layouts](https://developer.android.com/guide/topics/ui/declaring-layout)
    - **Navigation:** [Navigation Component](https://developer.android.com/guide/navigation) untuk mengelola alur antar layar.
    - **Database:** [Room Persistence Library](https://developer.android.com/training/data-storage/room) sebagai lapisan abstraksi di atas database SQLite lokal.
    - **Lifecycle & State Management:** [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) dan [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) untuk mengelola data UI yang sadar siklus hidup (*lifecycle-aware*).
- **Asynchronous Programming:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) untuk menangani operasi database di *background thread*.
- **Tampilan Daftar:** [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview) untuk menampilkan daftar transaksi dan anggaran secara efisien.
