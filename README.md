# 📁 Nexo — Gerenciador de Arquivos Nativo & Ultraleve para Android

Gerenciador de arquivos minimalista, de altíssima performance, com **zero anúncios**, **zero rastreadores** e foco em máxima fluidez (120Hz) para seu **Redmi 15**.

## 🚀 Funcionalidades do Nexo

1. **Acesso Total ao Armazenamento (`MANAGE_EXTERNAL_STORAGE`)**:
   - Sem as restrições chatas de *Scoped Storage* do Android 11+.
   - Lista todas as pastas do armazenamento interno (`/storage/emulated/0`), downloads, mídias e documentos.
2. **Zero Anúncios & Zero Bloatware**:
   - Sem banners, sem pop-ups, sem telemetria, sem consumo de dados em segundo plano.
3. **Navegação Rápida (Breadcrumbs)**:
   - Barra superior com caminho de pastas clicável para saltar para qualquer pasta ancestral instantaneamente.
4. **Filtro & Busca em Tempo Real**:
   - Digitação com filtragem instantânea sem travar a interface.
5. **Ações de Arquivo Essenciais**:
   - Criar novas pastas.
   - Renomear itens.
   - Excluir arquivos e diretórios recursivamente.
   - Abertura de arquivos com os apps padrão do sistema via `FileProvider`.
6. **Alternador de Arquivos Ocultos**:
   - Veja ou oculte arquivos que começam com ponto (`.folder` ou `.file`).
7. **Ordenação Inteligente**:
   - Nome (A-Z / Z-A), Data e Tamanho (mantendo sempre pastas no topo).
8. **Dark AMOLED Theme**:
   - Interface com fundo preto puro (`#101010` / `#000000`), poupando bateria na tela do Redmi.

---

## 🛠️ Como Compilar e Gerar o APK

### Opção 1: Pelo Android Studio (Recomendado)
1. Abra o **Android Studio**.
2. Clique em **File > Open** e selecione a pasta `C:\Users\soder\Documents\Nexo`.
3. Aguarde o Gradle sincronizar as dependências automaticamente.
4. Conecte seu Redmi 15 no USB (com *Depuração USB* ativada) e clique no botão **Run (Play)** ▶️.
5. Para gerar o APK instalável direto: vá em **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

### Opção 2: Pela Linha de Comando (Gradle)
Tendo o Android SDK configurado no PATH ou `local.properties`:
```powershell
.\gradlew assembleDebug
```
O arquivo APK será gerado em:
`app/build/outputs/apk/debug/app-debug.apk`

---

## 📱 Instalando no Redmi 15

### Via ADB:
```powershell
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Ou transferindo o arquivo:
1. Copie o arquivo `.apk` gerado para o celular via cabo USB ou Drive.
2. No celular, toque no `.apk` para instalar.
3. Ao abrir o **Nexo** pela primeira vez, toque em **"Permitir Acesso Total"** para liberar a leitura de todas as pastas do sistema.
