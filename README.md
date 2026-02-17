# Tugas Kecil 1 IF2211 Strategi Algoritma - Queens LinkedIn Game Brute Force Solver

## Deskripsi Singkat

Program ini adalah sebuah solver untuk permainan logika **Queens** yang tersedia di LinkedIn. Tujuan utama permainan ini adalah menempatkan *queen* pada papan persegi berwarna dengan aturan sebagai berikut:
1. Tiap baris hanya boleh memiliki satu ratu.
2. Tiap kolom hanya boleh memiliki satu ratu.
3. Tiap daerah warna hanya boleh memiliki satu ratu.
4. Ratu tidak boleh ditempatkan bersebelahan dengan ratu lainnya, termasuk secara diagonal.

Program ini menggunakan algoritma **Brute Force** murni untuk menemukan solusi penempatan ratu yang valid atau menginformasikan jika tidak ada solusi.

## Struktur Repositori

* **bin**: Berisi file executable atau hasil kompilasi program.
* **doc**: Berisi spesifikasi dan laporan tugas kecil dalam format PDF.
* **src**: Berisi *source code* program.
* **src/main/input**: Berisi file uji (*test case*) dalam format `.txt`.

## Requirement Program

* **Java Development Kit (JDK)** versi 17 atau lebih baru.
* **Apache Maven**

## Cara Kompilasi

```bash
mvn clean compile

```

## Cara Menjalankan

### 1. Menjalankan Antarmuka Grafis (GUI)
```bash
mvn javafx:run

```

### 2. Menjalankan Antarmuka Terminal (CLI)
```bash
java -cp target/classes queens.Solver

```

## Cara Penggunaan

1. Masukkan nama file *test case* (contoh: `tc.txt`) saat diminta oleh program.
2. Pastikan file tersebut berada di folder `src/main/input/`.
3. Program akan menampilkan:
* Visualisasi proses pencarian (*Live Update*).
* Hasil akhir penempatan ratu
* Banyak konfigurasi/kasus yang ditinjau.
* Waktu eksekusi dalam milidetik (*ms*).

## Dibuat Oleh
* **Nama**: Narendra Dharma Wistara M.
* **NIM**: 13524044
* **Program Studi**: Teknik Informatika, Institut Teknologi Bandung.

---

*Tugas ini disusun sebagai bagian dari pemenuhan mata kuliah IF2211 Strategi Algoritma Semester II tahun 2025/2026*.

