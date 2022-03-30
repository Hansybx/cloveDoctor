import { getCurrentInstance } from 'vue'

export default function getGlobalProperties() {
    const { appContext: { app: { config: { globalProperties } } } } = getCurrentInstance();
    return { ...globalProperties }
}