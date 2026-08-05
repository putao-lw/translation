# 浮译字幕 Android

Android 10+ 的免费方案原型：MediaProjection + AudioPlaybackCapture + 前台服务 + 全局悬浮窗。用户授权后可捕获其他 App 的媒体播放音频；来源 App 仍可通过 capture policy 禁止捕获。

## 构建

推送到 GitHub 后，打开 **Actions → Build APK**，完成后从 Artifacts 下载 `translation-debug-apk`。本仓库未提交签名密钥，生成的是 debug APK，仅供个人安装。

## 当前状态

音频捕获、权限和悬浮窗骨架已完成；英文 PCM 到实时字幕的识别引擎需要接入本地 Whisper/Vosk 模型（模型文件较大，不能直接放进仓库）。下一步接入模型后即可把识别结果送到 ML Kit 翻译并更新悬浮窗。抖音等 App 是否允许播放捕获由其自身策略决定。
